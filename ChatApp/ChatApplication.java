

import java.util.*;

class User {
    private String userId;
    private String userName;
    private String password;

    public User(String userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
}

class Message {
    private String content;
    private String sender;
    private long timestamp;
    private boolean deleted;

    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
        this.timestamp = System.currentTimeMillis();
        this.deleted = false;
    }

    public String getContent() {
        return deleted ? "(Deleted)" : content;
    }

    public String getSender() { return sender; }
    public long getTimestamp() { return timestamp; }

    public void delete() { deleted = true; }
}
    
class Chat {
    private String chatId;
    private List<Message> messages = new ArrayList<>();
    private Set<String> participants = new HashSet<>();

    public Chat(String chatId) {
        this.chatId = chatId;
    }

    public String getChatId() { return chatId; }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        // Sort messages in ascending order by timestamp (oldest messages first)
        messages.sort(Comparator.comparingLong(Message::getTimestamp));
        return messages;
    }

    public void addParticipant(String userName) {
        participants.add(userName);
    }

    public boolean isParticipant(String userName) {
        return participants.contains(userName);
    }

    public Set<String> getParticipants() {
        return participants;
    }
}

class ChatApplication {
    private Map<Integer, User> users = new HashMap<>();
    private Map<String, Chat> chats = new HashMap<>();
    private String loggedInUser = null;

    public ChatApplication() {
        initializeUsers();
    }

    private void initializeUsers() {
        users.put(100, new User("100", "Mani", "Mani12#4"));
        users.put(101, new User("101", "Ram", "RK^Kumar93"));
        users.put(102, new User("102", "Suresh", "Surr^Suj4"));
        users.put(103, new User("103", "Naresh", "12Nrh68"));
        users.put(104, new User("104", "Ganesh", "Kasjdk3"));
    }

    public boolean login(String userName, String password) {
        for (User user : users.values()) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                loggedInUser = userName;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        loggedInUser = null;
    }

    public void sendMessage(String recipient, String messageContent) {
        if (loggedInUser == null) return;

        String chatId = getChatId(loggedInUser, recipient);
        Chat chat = chats.computeIfAbsent(chatId, k -> new Chat(chatId));
        chat.addMessage(new Message(messageContent, loggedInUser));
        chat.addParticipant(loggedInUser);
        chat.addParticipant(recipient);
    }

    public List<Message> getChatMessages(String userName) {
        if (loggedInUser == null) return Collections.emptyList();
        String chatId = getChatId(loggedInUser, userName);
        return chats.getOrDefault(chatId, new Chat(chatId)).getMessages();
    }

    private String getChatId(String user1, String user2) {
        return user1.compareTo(user2) < 0 ? user1 + ":" + user2 : user2 + ":" + user1;
    }

    public void deleteMessage(String recipient, int messageIndex) {
        if (loggedInUser == null) return;

        String chatId = getChatId(loggedInUser, recipient);
        Chat chat = chats.get(chatId);
        if (chat != null && messageIndex >= 0 && messageIndex < chat.getMessages().size()) {
            Message message = chat.getMessages().get(messageIndex);
            if (message.getSender().equals(loggedInUser)) {
                message.delete();
                System.out.println("Message deleted successfully.");
            } else {
                System.out.println("You can only delete your own messages.");
            }
        } else {
            System.out.println("Message not found.");
        }
    }

    public void viewGroupMessages(String groupId) {
        Chat groupChat = chats.get(groupId);
        if (groupChat == null) {
            System.out.println("Group chat not found.");
            return;
        }

        System.out.println("Group chat history for " + groupId + ":");
        List<Message> messages = groupChat.getMessages();
        for (int i = 0; i < messages.size(); i++) {
            Message msg = messages.get(i);
            Date messageDate = new Date(msg.getTimestamp());
            System.out.println(i + ": [" + messageDate + "] " + msg.getSender() + ": " + msg.getContent());
        }
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. View Individual Chats");
            System.out.println("2. Group Chats");
            System.out.println("3. View Group Messages");
            System.out.println("4. Logout");
            System.out.println("5.Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    handleIndividualChats(scanner);
                    break;
                case 2:
                    handleGroupChats(scanner);
                    break;
                case 3:
                    handleViewGroupMessages(scanner);
                    break;
                case 4:
                    logout();
                    return;
                case 5:
                	System.out.println("Thank you Sir......! \n The end");
                	System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

   private void handleIndividualChats(Scanner scanner) {
    System.out.print("Enter the recipient's username: ");
    String recipient = scanner.nextLine();

    // Check if the recipient exists in the application
    boolean recipientExists = users.values().stream()
                                   .anyMatch(user -> user.getUserName().equals(recipient));

    if (recipientExists) {
        // Display existing messages with the recipient
        List<Message> messages = getChatMessages(recipient);
        if (messages.isEmpty()) {
            System.out.println("No chat history with " + recipient + ".");
        } else {
            System.out.println("Chat history with " + recipient + ":");
            for (int i = 0; i < messages.size(); i++) {
                Message msg = messages.get(i);
                Date messageDate = new Date(msg.getTimestamp());
                System.out.println(i + ": [" + messageDate + "] " + msg.getSender() + ": " + msg.getContent());
            }
        }

        // Send a new message
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();
        sendMessage(recipient, message);
        System.out.println("Message sent to " + recipient + ".");

        // Option to delete a message
        System.out.print("Do you want to delete a message? (yes/no): ");
        String deleteChoice = scanner.nextLine();
        if (deleteChoice.equalsIgnoreCase("yes")) {
            System.out.print("Enter the index of the message to delete: ");
            int messageIndex = scanner.nextInt();
            scanner.nextLine(); // consume newline
            deleteMessage(recipient, messageIndex);
        }
    } else {
        System.out.println("Recipient not found. Please enter a valid username.");
    }
}


private void handleGroupChats(Scanner scanner) {
    System.out.print("Enter the group ID to send a message (or create new group): ");
    String groupId = scanner.nextLine();

    // Check if the group already exists
    Chat groupChat = chats.get(groupId);
    if (groupChat == null) {
        // Create new group chat
        Set<String> participants = new HashSet<>();
        System.out.print("Enter participants (comma-separated): ");
        String[] partArray = scanner.nextLine().split(",");
        Collections.addAll(participants, partArray);
        groupChat = new Chat(groupId);
        for (String participant : participants) {
            groupChat.addParticipant(participant.trim());
        }
        chats.put(groupId, groupChat);
        System.out.println("Group chat created with ID: " + groupId);
    }

    // Send message in existing group
    System.out.print("Enter your message: ");
    String message = scanner.nextLine();
    groupChat.addMessage(new Message(message, loggedInUser));
    System.out.println("Your message sent to group " + groupId + ".");

    // Option to delete a message
    System.out.print("Do you want to delete a message in this group? (yes/no): ");
    String deleteChoice = scanner.nextLine();
    if (deleteChoice.equalsIgnoreCase("yes")) {
        List<Message> messages = groupChat.getMessages();
        System.out.println("Group chat history:");
        for (int i = 0; i < messages.size(); i++) {
            Message msg = messages.get(i);
            Date messageDate = new Date(msg.getTimestamp());
            System.out.println(i + ": [" + messageDate + "] " + msg.getSender() + ": " + msg.getContent());
        }
        
        System.out.print("Enter the index of the message to delete: ");
        int messageIndex = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (messageIndex >= 0 && messageIndex < messages.size()) {
            Message msgToDelete = messages.get(messageIndex);
            if (msgToDelete.getSender().equals(loggedInUser)) {
                msgToDelete.delete();
                System.out.println("Message deleted successfully from the group chat.");
            } else {
                System.out.println("You can only delete your own messages.");
            }
        } else {
            System.out.println("Invalid message index.");
        }
    }


    }

    private void handleViewGroupMessages(Scanner scanner) {
        System.out.print("Enter the group ID to view messages: ");
        String groupId = scanner.nextLine();
        viewGroupMessages(groupId);
    }

    public static void main(String[] args) {
        ChatApplication app = new ChatApplication();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (app.login(username, password)) {
                System.out.println("Login successful!");
                app.displayMenu();
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
    }
}
