import java.io.*;
import java.util.*;

//Start of Class
public class FileListMaker
{

    //Function to add an item to the list
    public static ArrayList<String> addItem(ArrayList<String> l) throws IOException
    {
        Scanner sc = new Scanner(System.in);
        String item;
        do
        {
            //Requesting user to enter the item to add
            System.out.print("\nEnter item to add: ");
            item = sc.nextLine();
            if(item.length() != 0)
            {
                //Adding Item to the List
                l.add(item);
                System.out.println("Item added successfully.");
            }
            else		//Showing error when empty string/data was supplied for an item
                System.out.println("Item data is empty. Please re-enter!");
        }while(item.length() <= 0);
        return l;
    }

    //Function to delete a particular item from the list
    public static ArrayList<String> delItem(ArrayList<String> l) throws IOException
    {
        Scanner sc = new Scanner(System.in);
        int n;

        viewList(l);
        if(l.size() == 0)
            return l;
        do
        {
            //Requesting user to enter the item index to delete
            System.out.print("\nEnter item index to delete: ");
            n = sc.nextInt();
            if(n >= 1)
            {
                //Deleting Item from the List
                l.remove(n-1);
                System.out.println("Item deleted successfully.");
            }
            else	//Showing error when invalid index was entered
                System.out.println("Item index is invalid. Please re-enter!");
        }while(n <= 0);
        return l;
    }

    //Function to load list data from a file on disk
    public static ArrayList<String> loadList(String name) throws IOException
    {
        ArrayList<String> lines = new ArrayList<String>();
        //Reading the file in Buffer
        BufferedReader in = new BufferedReader(new FileReader(new File(name)));
        String s ="";

        //Reading line-by-line data from buffer
        while((s = in.readLine()) != null)
            lines.add(s);			//Adding each line as item in the list
        System.out.println("\nList loaded successfully from disk.");
        return lines;
    }

    //Function to clear i.e. delete all elements of the list
    public static void clearList(ArrayList<String> l)
    {
        l.clear();			//Clearing all the items from the list
        System.out.println("\nList cleared successfully.");
    }

    //Function to save the list contents to a file on disk
    public static void saveList(ArrayList<String> l, String name) throws IOException
    {
        //Opening file-writer to write the file contents
        PrintWriter out = new PrintWriter(new FileWriter(new File(name)));
        int i;

        for(i = 0; i < l.size(); ++i)
            out.println(l.get(i));	//Writing list item to file one by one
        System.out.println("\nList saved successfully to disk.");
    }

    //Function to display the list data
    public static void viewList(ArrayList<String> l)
    {
        int i;
        if(l.size() > 0)
        {
            System.out.println();
            //Displaying the entire contents of the lsit
            for(i = 0; i < l.size(); ++i)
                System.out.println((i+1) + ") " + l.get(i));
        }
        else		//Showing message that list is empty
            System.out.println("List is empty!");

    }

    //main function i.e. to be executed first
    public static void main(String[] args) throws IOException
    {
        // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);
        char ch, d;
        boolean needsToBeSaved = false;
        //Initializing an array list for storing list items
        ArrayList<String> list = new ArrayList<String>();
        String file_name = "";

        loop:
        do
        {
            //Menu to be shown every time
            System.out.println("\n******MENU*****");
            System.out.println("A – Add an item to the list");
            System.out.println("D – Delete an item from the list");
            System.out.println("O – Open a list file from disk");
            System.out.println("S – Save the current list file to disk");
            System.out.println("C – Clear removes all the elements from the current list");
            System.out.println("V - View the current list");
            System.out.println("Q – Quit the program");
            //Requesting user choice
            System.out.print("\nEnter your choice: ");
            ch = sc.next().toUpperCase().charAt(0);
            switch(ch)
            {
                case 'A':
                    //Calling addItem() function to add item in the list
                    list = addItem(list);
                    needsToBeSaved = true;		//Updating the flag for changes
                    break;
                case 'D':
                    //Calling delItem() function to delete item from the list
                    list = delItem(list);
                    needsToBeSaved = true;		//Updating the flag for changes
                    break;
                case 'O':
                    //Checking if saving required
                    if(needsToBeSaved == true && (list.size() > 0 || file_name != "" ))
                    {
                        System.out.println("\nCurrent List is not saved yet. Do you really want to discard current list & load list from file? (Y/N) ");
                        d = sc.next().toUpperCase().charAt(0);
                        if(d == 'Y')
                        {
                            System.out.print("\nEnter list filename: ");
                            sc.nextLine();
                            file_name = sc.nextLine();
                            list = loadList(file_name);
                        }
                    }
                    else
                    {
                        System.out.print("\nEnter list filename: ");
                        sc.nextLine();
                        file_name = sc.nextLine();
                        list = loadList(file_name);
                    }
                    break;
                case 'S':
                    if(file_name == null)
                    {
                        System.out.print("\nEnter filename for saving the list: ");
                        file_name = sc.nextLine();
                    }
                    saveList(list, file_name);
                    needsToBeSaved = false;		//Updating the flag for changes
                    break;
                case 'C':
                    clearList(list);
                    needsToBeSaved = false;		//Updating the flag for changes
                    break;
                case 'V':
                    viewList(list);
                    break;
                case 'Q':
                    if(needsToBeSaved == true  && (list.size() > 0 || file_name != "" ))
                    {
                        System.out.println("\nCurrent List is not saved yet and is gone once you exit. Do you really want to quit? (Y/N) ");
                        d = sc.next().toUpperCase().charAt(0);
                        if(d == 'Y')
                        {
                            break loop;
                        }
                    }
                    else
                        break loop;
                    break;
                default:
                    System.out.println("Invalid choice. Please re-try!");
            }
        }while(true);
        System.out.println("\nBye Bye!");
    }
}