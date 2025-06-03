import java.util.*;

public class BillerSlipping {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int amt_spt = 0;
        int memberCount = 0;
        List<String[]> members = new ArrayList<>();

        try {
            // 1. Get amount
            System.out.println("Enter the amount spent : ");
            amt_spt = Integer.parseInt(sc.nextLine().trim());

            // 2. Get member count
            System.out.println("Enter the number of members : ");
            memberCount = Integer.parseInt(sc.nextLine().trim());

            // 3. Get members
            for (int i = 0; i < memberCount; i++) {
                System.out.println("Enter member ID: ");
                String id = sc.nextLine().trim();

                System.out.println("Enter member Name: ");
                String name = sc.nextLine().trim();

                members.add(new String[]{id, name});
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numeric values where required.");
            return;
        }

        // 4. Payer ID
        System.out.println("Enter the payer ID:");
        String p_id = sc.nextLine().trim();

        // Validate payer ID
        boolean payerExists = false;
        for (String[] member : members) {
            if (member[0].equals(p_id)) {
                payerExists = true;
                break;
            }
        }
        if (!payerExists) {
            System.out.println("Payer ID not found among members.");
            return;
        }

        // 5. Split type
        System.out.println("Type the split type (eqly or uneqly):");
        String checkSplit = sc.nextLine().trim().toLowerCase();

        if (checkSplit.equals("eqly")) {
            splitEqually(amt_spt, members, p_id);
        } else if (checkSplit.equals("uneqly")) {
            List<String[]> unevenSplit = new ArrayList<>();
            System.out.println("Enter uneven split for each member (ID Amount):");
            for (int i = 0; i < memberCount; i++) {
                System.out.println("Enter ID:");
                String id = sc.nextLine().trim();
                System.out.println("Enter amount:");
                String amtStr = sc.nextLine().trim();
                unevenSplit.add(new String[]{id, amtStr});
            }
            splitUnequally(amt_spt, members, p_id, unevenSplit);
        } else {
            System.out.println("Invalid split type! Choose 'eqly' or 'uneqly'.");
        }
    }

    // Split Equally Method
    public static void splitEqually(int amount, List<String[]> members, String payerId) {
        int share = amount / members.size();
        int payerGets = 0;

        System.out.println("Successfully split \"Equally\"....");

        for (String[] member : members) {
            String id = member[0];
            String name = member[1];
            if (!id.equals(payerId)) {
                System.out.println(name + " owes " + share + " to " + getNameById(members, payerId) + ".");
                payerGets += share;
            }
        }
        System.out.println(getNameById(members, payerId) + " will get " + payerGets);
    }

    // Split Unequally Method
    public static void splitUnequally(int amount, List<String[]> members, String payerId, List<String[]> unevenSplit) {
        int total = 0;
        Map<String, Integer> splitMap = new HashMap<>();

        try {
            for (String[] entry : unevenSplit) {
                String id = entry[0];
                int amt = Integer.parseInt(entry[1]);
                splitMap.put(id, amt);
                total += amt;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount in uneven split. All values must be integers.");
            return;
        }

        if (total < amount) {
            System.out.println("The amount spent is greater than the sum of the \"unequally\" share");
            return;
        } else if (total > amount) {
            System.out.println("The amount spent is less than the sum of the \"unequally\" share");
            return;
        }

        System.out.println("Successfully split \"UnEqually\"....");

        int payerGets = 0;
        for (Map.Entry<String, Integer> entry : splitMap.entrySet()) {
            String id = entry.getKey();
            int val = entry.getValue();

            if (!id.equals(payerId)) {
                System.out.println(getNameById(members, id) + " owes " + val + " to " + getNameById(members, payerId) + ".");
                payerGets += val;
            }
        }

        System.out.println(getNameById(members, payerId) + " will get " + payerGets);
    }

    // Helper: Get Name by ID
    public static String getNameById(List<String[]> members, String id) {
        for (String[] member : members) {
            if (member[0].equals(id)) {
                return member[1];
            }
        }
        return "Unknown";
    }
}
