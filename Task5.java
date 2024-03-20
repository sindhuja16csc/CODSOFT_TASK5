import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class Contact  {
    private String name;
    private String mbNo;
    private String email;
    public Contact(String name, String mbNo, String email) {
        this.name = name;
        this.mbNo = mbNo;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return mbNo;
    }
    public String getEmail() {
        return email;
    }
    public Boolean validateMbNo(String mbno)
    {
        if(mbNo.length() == 10 )
        {
            for(int i=0;i<10;i++)
            {
                if(!Character.isDigit(mbNo.charAt(i)))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public Boolean validateEmail(String email )
    { 
            for(int i=0;i<email.length();i++)
            {
                if(email.charAt(i)== '@')
                {
                     return true;
                }
            }
        return false;
    }
    @Override
    public String toString() {
        return "Name: " + name + "\n Phone Number: " + mbNo +"\n Email Address: " + email +"/n";
    }
}
class AddressBookSystem {
    private List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }
    public void removeContact(String mbNo) {
        contacts.removeIf(contact -> contact.getPhoneNumber().equals(mbNo));
    }
    public Contact  findContact(String mbNo ) {
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(mbNo)) {
                return contact;
            }
        }
        return null;
    }
    public List<Contact> getAllContacts() {
        return contacts;
    }
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            contacts = (List<Contact>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
public class Task5 {
    public static void main(String[] args) {
        AddressBookSystem abs = new AddressBookSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Address Book System");
            System.out.println("1. Add New Contact ");
            System.out.println("2. Delete Contact ");
            System.out.println("3. Search Contact ");
            System.out.println("4. All Contacts ");
            System.out.println("5. Import Contacts ");
            System.out.println("6. Export Contacts ");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    System.out.print("First Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Phone Number: ");
                    String mbNo = scanner.nextLine(); 
                    System.out.print("Email Address: ");
                    String email = scanner.nextLine();
                    Contact contact = new Contact(name, mbNo, email);
                    if((contact.validateMbNo(mbNo)== true)&&(contact.validateEmail(email)== true))
                    {
                        abs.addContact(contact);
                        System.out.println("\n**Contact is Saved**\n");
                    }
                    else
                    {
                        System.out.println("\tERROR: * Please Enter Valid Inputs *\n");
                    }
                    break;
                case 2:
                    System.out.print("Enter Phone number to Remove: ");
                    String mbNoToRemove = scanner.nextLine(); 
                    Contact deleteContact = abs.findContact(mbNoToRemove);
                    if (deleteContact != null) 
                    {
                        System.out.println("Contact found");
                        System.out.println(deleteContact);
                        System.out.println("Do you Want to Delete the Contact Permenantly ??[Y/N]");
                        char ans = scanner.next().charAt(0);
                        if(ans == 'y'||ans == 'Y')
                        {
                            abs.removeContact(mbNoToRemove);
                            System.out.println("**Contact is Deleted **\n");
                        }
                    } 
                    else {
                        System.out.println("** No Results \n Check the Phone Number You Entered .**\n");
                    }
                    break;
                case 3:
                    System.out.print("Enter Phone Number to Search: ");
                    String mbToSearch = scanner.nextLine(); 
                    Contact foundContact = abs.findContact(mbToSearch);
                    if (foundContact != null) {
                        System.out.println("Contact found:\n\t" + foundContact);
                    } else {
                        System.out.println("** No Results \n Check the Phone Number You Entered .**\n");
                    }
                    break;
                case 4:
                    List<Contact> allContacts = abs.getAllContacts();
                    if (allContacts.isEmpty()) {
                        System.out.println("**No Contacts in the Book.**\n");
                    } else {
                        for (Contact s : allContacts) {
                            System.out.println(s);
                        }
                    }
                    break;
                case 5:
                    abs.saveToFile("contacts.dat");
                    System.out.println("Data saved to file.");
                    break;
                case 6:
                    abs.loadFromFile("contacts.dat");
                    System.out.println("Data loaded from file.");
                    break;
                case 7:
                    System.out.println("Exiting application.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please Try Again.\n");
            }
        }
    }
}
