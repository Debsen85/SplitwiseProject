import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import User.UserDetails;

public class Splitwise {

    HashMap<String, UserDetails> group;

    public Splitwise() {
        this.group = new HashMap<String, UserDetails>();
    }

    public void addExpenseEqual(String userPaying, Integer noOfUsers, String []users, Integer amount) {
        Integer toBePaid = amount / noOfUsers; 
        for (String key : users) {
            if (key.equals(userPaying)) {
                for (String innerKey : users) {
                    if (!innerKey.equals(userPaying)) {
                        if (group.get(key).balance.containsKey(innerKey)) {
                            group.get(key).balance.put(innerKey, group.get(key).balance.get(innerKey) - toBePaid);
                        } else {
                            group.get(key).balance.put(innerKey, - toBePaid);
                        }
                    }
                }
            } else {
                try {
                    group.get(key).balance.put(userPaying, group.get(key).balance.get(userPaying) + toBePaid);
                } catch (NullPointerException ex) {
                    group.get(key).balance.put(userPaying, toBePaid);
                }
            }
        }
    }

    private void addExpenseExact(String userPaying, Integer noOfUsers, String[] users, Integer amount,
            Integer[] amounts) {
        for (int i = 0; i < amounts.length; i++) {
            try {
                group.get(users[i]).balance.put(userPaying, group.get(users[i]).balance.get(userPaying) + amounts[i]);
            } catch (NullPointerException ex) {
                group.get(users[i]).balance.put(userPaying, amounts[i]);
            }
            try {
                group.get(userPaying).balance.put(users[i], group.get(userPaying).balance.get(users[i]) - amounts[i]);
            } catch (NullPointerException ex) {
                group.get(userPaying).balance.put(users[i], - amounts[i]);
            }
        } 
    }

    private void addExpensePercent(String userPaying, Integer noOfUsers, String[] users, Integer amount,
    Integer[] amounts) {
        for (int i = 0; i < amounts.length; i++) {
            if (!users[i].equals(userPaying)) {
                try {
                    group.get(users[i]).balance.put(userPaying, group.get(users[i]).balance.get(userPaying) + amounts[i]);
                } catch (NullPointerException ex) {
                    group.get(users[i]).balance.put(userPaying, amounts[i]);
                }
                try {
                    group.get(userPaying).balance.put(users[i], group.get(userPaying).balance.get(users[i]) - amounts[i]);
                } catch (NullPointerException ex) {
                    group.get(userPaying).balance.put(users[i], - amounts[i]);
                }
            }
        } 
    }

    private void show(String user) {
        Set<String> uniquSet = new HashSet<String>();
        if (group.get(user).balance.isEmpty()) {
            System.out.println("No balances");
        } else {
            for (String key : group.keySet()) {
                if (key.equals(user)) {
                    for (String innerKey : group.get(key).balance.keySet()) {
                        if (group.get(key).balance.get(innerKey) < 0) {
                            uniquSet.add(innerKey + " owes " + key + ": " + (-group.get(key).balance.get(innerKey)));
                        } else if (group.get(key).balance.get(innerKey) > 0) {
                            uniquSet.add(key + " owes " + innerKey + ": " + (group.get(key).balance.get(innerKey)));
                        }
                    }
                }
            }
        }
        for (String value : uniquSet) {
            System.out.println(value);
        }
    }

    private void showAll() {
        Set<String> uniquSet = new HashSet<String>();
        int count = 0;
        for (String key : group.keySet()) {
            if (!group.get(key).balance.isEmpty()) {
                for (String innerKey : group.get(key).balance.keySet()) {
                    if (group.get(key).balance.get(innerKey) < 0) {
                        uniquSet.add(innerKey + " owes " + key + ": " + (-group.get(key).balance.get(innerKey)));
                    } else if (group.get(key).balance.get(innerKey) > 0) {
                        uniquSet.add(key + " owes " + innerKey + ": " + (group.get(key).balance.get(innerKey)));
                    }
                }
            } else {
                count++;
            }
        }
        if (count == 4) {
            System.out.println("No balances");
        }
        for (String value : uniquSet) {
            System.out.println(value);
        }
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        Splitwise splitwise = new Splitwise();

        UserDetails userOne = new UserDetails("Debayan", "debsen8@gmail.com", "7044223891");
        UserDetails userTwo = new UserDetails("Rahul", "rahul@gmail.com", "7044223892");
        UserDetails userThree = new UserDetails("Shubham", "shubham@gmail.com", "7044223893");
        UserDetails userFour = new UserDetails("Sandip", "sandip@gmail.com", "7044223894");

        splitwise.group.put("User1", userOne);
        splitwise.group.put("User2", userTwo);
        splitwise.group.put("User3", userThree);
        splitwise.group.put("User4", userFour);

        Scanner command = new Scanner(System.in);
        Boolean flag = true;
        System.out.println();
        System.out.println();
        System.out.println("<--------Splitwise-------->");
        System.out.println();
        System.out.println();
        while(flag) {
            String inputCommand[] = command.nextLine().split(" ");
            switch (inputCommand[0]) {
                case "EXPENSE":
                    System.out.println();
                    System.out.println();
                    String userPaying  = "User" + inputCommand[1].substring(1, 2);
                    Integer noOfUsers = Integer.parseInt(inputCommand[3]);
                    Integer amount = Integer.parseInt(inputCommand[2]);
                    String users[] = new String[noOfUsers];
                    int i = 0, j = 4;
                    for (i = 0; i < noOfUsers; i++) {
                        users[i] = "User" + inputCommand[j].substring(1, 2);
                        j++;
                    }
                    if (inputCommand[inputCommand.length - 1].equals("EQUAL")) {
                        splitwise.addExpenseEqual(userPaying, noOfUsers, users, amount);
                    } else if (inputCommand[inputCommand.length - noOfUsers - 1].equals("EXACT")) {
                        Integer amounts[] = new Integer[noOfUsers];
                        j = inputCommand.length - noOfUsers;
                        for (i = 0; i < noOfUsers; i++) {
                            amounts[i] = Integer.parseInt(inputCommand[j]);
                            j++;
                        }
                        splitwise.addExpenseExact(userPaying, noOfUsers, users, amount, amounts);
                    } else {
                        Integer amounts[] = new Integer[noOfUsers];
                        j = inputCommand.length - noOfUsers;
                        for (i = 0; i < noOfUsers; i++) {
                            amounts[i] = (amount * Integer.parseInt(inputCommand[j])) / 100;
                            j++;
                        }
                        splitwise.addExpensePercent(userPaying, noOfUsers, users, amount, amounts);
                    }
                    System.out.println();
                    System.out.println();
                    break;
                case "SHOW":
                    System.out.println();
                    System.out.println();
                    if (inputCommand.length == 1) {
                        splitwise.showAll();
                    } else {
                        String user = "User" + inputCommand[1].substring(1, 2);
                        splitwise.show(user);
                    }
                    System.out.println();
                    System.out.println();
                    break;
                case "EXIT":
                    flag = false;
                    for (String outerKey : splitwise.group.keySet()) {
                        System.out.println(outerKey + ": {");
                        for (String innerKey : splitwise.group.get(outerKey).balance.keySet()) {
                            System.out.println(innerKey + ": " + splitwise.group.get(outerKey).balance.get(innerKey));
                        }
                        System.out.println("}");
                        System.out.println();
                    }
                    break;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
        }
    }
}