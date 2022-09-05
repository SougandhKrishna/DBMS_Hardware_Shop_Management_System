import java.sql.*;
import java.util.Scanner;

public class Hardwareshop
{
//Set JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
//static final String DB_URL = "jdbc:mysql://localhost/companydb";
   static final String DB_URL = "jdbc:mysql://localhost/Hardware_shop?useSSL=false";
//  Database credentials
   static final String USER = "root";// add your user 
   static final String PASS = "admin";// add password
   

public static void main(String[] args) 
   {
   Connection conn = null;
   Statement stmt = null;

   try
   {
      Class.forName(JDBC_DRIVER);
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
      System.out.println("Creating statement...");
      stmt = conn.createStatement();

      Scanner inp = new Scanner(System.in);

      clearScreen();
      System.out.println("\nWELCOME TO JOHN'S HARDWARE SHOP\n");     
      main_menu(stmt, inp);

      inp.close();
      stmt.close();
      conn.close();
   }
   
   catch(SQLException se)
   {    	 //Handle errors for JDBC
      se.printStackTrace();
   }
   
   catch(Exception e)
   {        	//Handle errors for Class.forName
      e.printStackTrace();
   }
   finally
   {				//finally block used to close resources
   try
   {
      if(stmt!=null)
         stmt.close();
   }
   catch(SQLException se2)
   {
   }
   try
   {
      if(conn!=null)
         conn.close();
   }
   catch(SQLException se)
   {
      se.printStackTrace();
   }					//end finally try
   }					//end try
   System.out.println("End of Code");

   }


/*
// STEP 2. Connecting to the Database
   try{
      //STEP 2a: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2b: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
      //STEP 2c: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();

//STEP 3: Query to database
      String sql;
      sql = "SELECT fname, lname from employee";
      ResultSet rs = stmt.executeQuery(sql);

//STEP 4: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         String fname  = rs.getString("fname");
         String lname = rs.getString("lname");

         //Display values
         System.out.print("fname: " + fname);
         System.out.println(", lname: " + lname);
      			}

//STEP 5: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
	}catch(SQLException se){    	 //Handle errors for JDBC
      	se.printStackTrace();
   	}catch(Exception e){        	//Handle errors for Class.forName
      e.printStackTrace();
   }finally{				//finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }					//end finally try
   }					//end try
   System.out.println("End of Code");
}					//end main
}					//end class


//Note : By default autocommit is on. you can set to false using con.setAutoCommit(false)
*/


static void main_menu(Statement stmt, Scanner inp)
{
   System.out.println("Login as : ");
   System.out.println("1. Customer");
   System.out.println("2. Shop Keeper");
   System.out.println("3. Shop Owner");
   System.out.println("0. Exit");

   System.out.print("\nENTER YOUR CHOICE : "); 
   int choice = Integer.parseInt(inp.nextLine());

   clearScreen(); 
   switch (choice) 
   {
      case 0:
         System.out.println("\nTHANK YOU!!\n\n");
         System.exit(0);
      case 1:
         customer_menu(stmt, inp);
         break;
      case 2:
         shopkeeper_login(stmt,inp);
         break;
      case 3:
         shopowner_login(stmt, inp);
         break;
      default:
         clearScreen();
         System.out.println("Please Enter a Valid Input!!\n");
         break;
   }
   main_menu(stmt, inp);
}


static void customer_menu(Statement stmt, Scanner inp)
{
   System.out.println("Please select any of the options: ");
   System.out.println("1. List of Items");
   System.out.println("0. Back");

   System.out.print("\nENTER YOUR CHOICE : ");  
   int choice =Integer.parseInt(inp.nextLine());
   
   clearScreen();
   switch(choice)
   {
      case 0:
         return;
      case 1:
         list_of_items(stmt,inp,1);
         break;
      default:
         clearScreen();
         System.out.println("Please Enter a Valid Input!!\n");
         break;         
   }
   customer_menu(stmt, inp);
}

static boolean login(Statement stmt, Scanner inp, boolean isshopowner) 
{
   System.out.print("Enter your ID: ");
   String id = inp.nextLine();
   System.out.print("Enter your Password: ");
   String password = inp.nextLine();

   clearScreen();
   boolean loggedin = false;

   if (isshopowner) 
   {
      String sql = "SELECT * from shopowner";
      ResultSet rs = execute(stmt, sql);

      try 
      {
         while (rs.next())
         {
            String saved_id = rs.getString("so_id");
            String saved_password = rs.getString("so_password");

            if (saved_id.equals(id) && saved_password.equals(password)) 
            {
               loggedin = true;
               break;
            }
         }
      }
      catch (SQLException se) 
      {
      }
   } 
   else 
   {
      String sql = "SELECT * from shopkeeper";
      ResultSet rs = execute(stmt, sql);

      try {
         while (rs.next()) 
         {
            String saved_id = rs.getString("sk_id");
            String saved_password = rs.getString("sk_password");

            if (saved_id.equals(id) && saved_password.equals(password))
            {
               loggedin = true;
               break;
            }
         }
      } catch (SQLException se) 
      {
      }
   }

   return loggedin;
}

static void shopkeeper_login(Statement stmt, Scanner inp) 
{
   clearScreen();
   if (login(stmt, inp, false)) 
   {
      shopkeeper_menu(stmt, inp);
   } 
   else 
   {
      System.out.print("Incorrect Credentials.\n\nType Y to give inputs again\nAny other input to go back to Main Menu \n");
      String input = inp.nextLine();
      if (input.equals("Y")||input.equals("y"))
         shopkeeper_login(stmt, inp);
      else
      {
         clearScreen();
         return;
      }
   }
}

static void shopowner_login(Statement stmt, Scanner inp) 
{
   clearScreen();
   if (login(stmt, inp, true)) 
   {
      shopowner_menu(stmt, inp);
   } 
   else {
      System.out.print("Incorrect Credentials.\n\nType Y to give inputs again\nAny other input to go back to Main Menu \n");
      String input = inp.nextLine();
      if (input.equals("Y")||input.equals("y"))
         shopowner_login(stmt, inp);
      else
         {
            clearScreen();
            return;
         }
   }
}

static void shopkeeper_menu(Statement stmt, Scanner inp) 
{
   System.out.println("Please select any option: ");
   System.out.println("1. List of available items");
   System.out.println("2. Sell an item");
   System.out.println("3. List of all items");
   System.out.println("4. List of items which are about to end");
   System.out.println("5. Add a new item");
   System.out.println("6. Increase Quantity of already existing item");
   System.out.println("7. Purchase History");  
   System.out.println("0. Back");

   System.out.print("\n\nENTER YOUR CHOICE : ");
   int choice = Integer.parseInt(inp.nextLine());
   clearScreen();

   switch (choice) 
   {
      case 0:
         return;
      case 1:
         list_of_items(stmt, inp,1);
         break;
      case 2:
         sell_item(stmt, inp);
         break;
      case 3:
         list_of_items(stmt, inp, 2);
         break;
      case 4:
         list_of_items(stmt,inp,3);
         break;
      case 5:
         add_new_item(stmt, inp);
         break;
      case 6:
         add_item(stmt, inp);
         break;      
      case 7:
         purchase_history(stmt,inp);
         break;
      default:
         clearScreen();
         System.out.println("Please Enter a Valid Choice!!\n");
         break;
   }
   shopkeeper_menu(stmt,inp);
}


static void shopowner_menu(Statement stmt, Scanner inp)
{
   System.out.println("Please select an option: ");
   System.out.println("1. List of Shopkeepers");
   System.out.println("2. Add a Shopkeeper");
   System.out.println("3. Delete a Shopkeeper");
   System.out.println("4. Purchase History");
   System.out.println("5. Specific Purchase History");
   System.out.println("0. Back");

   System.out.print("\n\nENTER YOUR CHOICE : ");
   int choice = Integer.parseInt(inp.nextLine());
   clearScreen();

   switch (choice) 
   {
      case 0:
         return;
      case 1:
         list_of_shopkeepers(stmt, inp);
         break;
      case 2:
         add_shopkeeper(stmt, inp);
         break;
      case 3:
         delete_shopkeeper(stmt, inp);
         break;
      case 4:
         purchase_history(stmt, inp);
         break;
      case 5:
         specific_purchase_history(stmt,inp);
         break;
      default:
         clearScreen();
         System.out.println("Please Enter a Valid Choice!!\n");
         break;
   }
   shopowner_menu(stmt, inp);
}

static boolean list_of_items(Statement stmt, Scanner inp, Integer aaa) 
{
   String sql = "select * from items";
   ResultSet rs = execute(stmt, sql);
   boolean noitems = true;

   try {
      System.out.println("List of Items:\n");
      while (rs.next()) {
         Integer iid = rs.getInt("iid");
         String iname = rs.getString("iname");
         Integer iprice = rs.getInt("iprice");
         Integer iquantity = rs.getInt("iquantity");

         if (aaa==1) 
         {
            if (iquantity>0) 
            {
               System.out.println("Item ID : " + iid);
               System.out.println("Item Name : " + iname);
               System.out.println("Item Price : " + iprice);
               System.out.println("Quantity Left : " + iquantity);
               System.out.println("");
               noitems = false;
            }
         } 
         
         else if (aaa==2)
         {
            System.out.println("Item ID : " + iid);
            System.out.println("Item Name : " + iname);
            System.out.println("Item Price : " + iprice);
            System.out.println("Quantity Left : " + iquantity);
            System.out.println("");
            noitems = false;
         }
         else if (aaa==3)
         {
            if (iquantity<3) 
            {
               System.out.println("Item ID : " + iid);
               System.out.println("Item Name : " + iname);
               System.out.println("Item Price : " + iprice);
               System.out.println("Quantity Left : " + iquantity);
               System.out.println("");
               noitems = false;
            }

         }
      }

      if (noitems)
         System.out.println("Sorry, no items are available!\n");

      rs.close();
   } catch (SQLException e) 
   {
      e.printStackTrace();
   }
   return noitems;
}

static void sell_item(Statement stmt, Scanner inp) {
   try {
      boolean noitems = list_of_items(stmt, inp, 1);
      if (!noitems) 
      {
         System.out.print("\nEnter Item ID : ");
         Integer iid = Integer.parseInt(inp.nextLine());

         System.out.print("\nEnter Quantity : ");
         Integer iquantity = Integer.parseInt(inp.nextLine());

         String sql = String.format("select * from items WHERE iid = '%d'",iid);
         ResultSet rs = execute(stmt, sql);   
         
         if(!rs.next())
         {
            clearScreen();
            System.out.println("Please Enter a valid Item ID.\n\n");
            sell_item(stmt, inp);
         }
         
         
         Integer av_quantity = rs.getInt("iquantity");
         
         if(av_quantity<iquantity)
         {
            clearScreen();
            System.out.println("Only "+av_quantity+" of the required item is available. Please enter a valid quantity.\n\n");
            sell_item((stmt), inp);
         }

         if(iquantity<=0)
         {
            clearScreen();
            System.out.println("Please Enter a valid quantity.\n\n");
            sell_item(stmt, inp);
         }

         System.out.print("\nEnter Customer Name : ");
         String cname = inp.nextLine();

         System.out.print("\nEnter Phone Number : ");
         String phno = inp.nextLine();

         clearScreen();

         sql = String.format("UPDATE items SET iquantity = iquantity - '%d' WHERE iid = '%d'", iquantity, iid);
         Integer result = update(stmt, sql);
         
         if (result != 0)
            System.out.println("ITEM RECORD HAS BEEN UPDATED SUCCESFULLY!!\n");
         else
            System.out.println("Something went wrong!\n");
         
         sql = String.format("INSERT INTO purchases (cname,phone_number,item_id,quantity) VALUES('%s', '%s', '%d', '%d')",cname, phno, iid,iquantity);
         result = update(stmt, sql);
         
         if (result != 0)
         System.out.println("PURCHASE RECORD HAS BEEN UPDATED SUCCESFULLY!!\n");
         else
         System.out.println("Something went wrong!\n");
      }
   } catch (Exception e) {
      e.printStackTrace();
   }
}

static void add_new_item(Statement stmt, Scanner inp) 
{
   try 
   {
      System.out.print("\nEnter Item Name : ");
      String iname = inp.nextLine();
      System.out.print("\nEnter Item Price : ");
      Integer iprice = Integer.parseInt(inp.nextLine());
      System.out.print("\nEnter Item Quantity : ");
      Integer iqty = Integer.parseInt(inp.nextLine());
      clearScreen();

      String sql = String.format(
            "INSERT INTO items (iname,iprice,iquantity) VALUES('%s', '%d', '%d')",
            iname, iprice, iqty);
      int result = update(stmt, sql);

      if (result != 0)
         System.out.println("Item has been added successfully!!\n");
      else
         System.out.println("Something went wrong!\n");
   } 
   catch (Exception e) 
   {
      e.printStackTrace();
   }
}

static void add_item(Statement stmt, Scanner inp) 
{
   try 
   {
      System.out.print("\nEnter Item Id : ");
      Integer iid = Integer.parseInt(inp.nextLine());
      System.out.print("\nEnter Quantity : ");
      Integer iqty = Integer.parseInt(inp.nextLine());
      clearScreen();

      String sql = String.format("UPDATE items SET iquantity = iquantity + '%d' WHERE iid = '%d'", iqty, iid);
      Integer result = update(stmt, sql);
      
      if (result != 0)
         System.out.println("ITEM RECORD HAS BEEN UPDATED SUCCESFULLY!!\n");
      else
         System.out.println("Something went wrong!\n");
   } 
   catch (Exception e) 
   {
      e.printStackTrace();
   }
}

static void purchase_history(Statement stmt, Scanner inp) 
{
   String sql = "select * from purchases";
   ResultSet rs = execute(stmt, sql);
   int count=0;
   
   clearScreen();
   try {
      System.out.println("Purchase History :\n");
      while (rs.next()) 
      {
         Integer sno=rs.getInt("serial_no");
         String cname = rs.getString("cname");
         String phno = rs.getString("phone_number");
         Integer iid=rs.getInt("item_id");
         Integer qty=rs.getInt("quantity");        

         System.out.println("Serial No.: " + sno);
         System.out.println("Customer Name: " + cname);
         System.out.println("Customer Phone Number: " + phno);
         System.out.println("Item ID: " + iid);
         System.out.println("Quantity: " + qty);         

         System.out.println("\n");
         count++;
      }

      rs.close();
   } catch (SQLException e) {
      e.printStackTrace();
   }
   if(count==0)
   {
      System.out.println("\nThere are no purchases so far\n\n");          
   }
}

static void specific_purchase_history(Statement stmt, Scanner inp) 
{
   String sql = "select * from purchases";
   ResultSet rs = execute(stmt, sql);

   System.out.print("Enter Customer Name: ");
   String input = inp.nextLine();

   clearScreen();

   int count=0;

   try {

      while (rs.next()) 
      {
         Integer sno=rs.getInt("serial_no");
         String cname = rs.getString("cname");
         String phno = rs.getString("phone_number");
         Integer iid=rs.getInt("item_id");
         Integer qty=rs.getInt("quantity");
         
         if(input.equals(cname))
         {
         System.out.println("Purchase History of "+input+":\n");
         System.out.println("Serial No.: " + sno);
         System.out.println("Customer Name: " + cname);
         System.out.println("Customer Phone Number: " + phno);
         System.out.println("Item ID: " + iid);
         System.out.println("Quantity: " + qty);         

         System.out.println("\n\n");
         count++;
         }
      }

      if(count==0)
         System.out.println("\n\nNo records of "+input+" found\n\n");

      rs.close();
   } catch (SQLException e) {
      e.printStackTrace();
   }
}

static void list_of_shopkeepers(Statement stmt, Scanner inp) 
{
   String sql = "select * from shopkeeper";
   ResultSet rs = execute(stmt, sql);

   try {
      System.out.println("List of Shopkeepers :\n");
      while (rs.next()) 
      {
         String name = rs.getString("sk_name");
         String id = rs.getString("sk_id");

         System.out.println("Shopkeeper ID : " + id);
         System.out.println("Shopkeeper Name: " + name);

         System.out.println("\n");
      }

      rs.close();
   } catch (SQLException e) {
      e.printStackTrace();
   }
}

static void add_shopkeeper(Statement stmt, Scanner inp) 
{
   try 
   {
      System.out.print("Enter Shopkeeper ID : ");
      String id = inp.nextLine();
      System.out.print("Enter Shopkeeper Name : ");
      String name = inp.nextLine();
      System.out.print("Enter Shopkeeper Password : ");
      String password = inp.nextLine();

      clearScreen();

      String sql = String.format("INSERT INTO shopkeeper (sk_id,sk_name,sk_password) VALUES('%s', '%s', '%s')", id, name, password);
      int result = update(stmt, sql);

      if (result != 0)
         System.out.println("Shopkeeper has been added successfully!!\n");
      else
         System.out.println("Something went wrong!\n");
   } catch (Exception e) {
      e.printStackTrace();
   }
}

static void delete_shopkeeper(Statement stmt, Scanner inp) 
{
   try {
      System.out.print("Enter Shopkeeper ID: ");
      String id = inp.nextLine();

      clearScreen();

      String sql = String.format("DELETE FROM shopkeeper where sk_id = '%s'", id);
      int result = update(stmt, sql);

      if (result != 0)
         System.out.println("Shopkeeper details have been deleted successfully!!\n");
      else
         System.out.println("Something went wrong!\n");
   } catch (Exception e) {
      e.printStackTrace();
   }
}

static ResultSet execute(Statement stmt, String sql) 
{
   try
   {
      ResultSet rs = stmt.executeQuery(sql);
      return rs;
   } 
   catch (SQLException se){
      se.printStackTrace();
   } 
   catch (Exception e){
      e.printStackTrace();
   }
   return null;
}

static int update(Statement stmt, String sql) 
{
   try 
   {
      int rs = stmt.executeUpdate(sql);
      return rs;
   } 
   catch (SQLException se) {
      se.printStackTrace();
   } 
   catch (Exception e) {
      e.printStackTrace();
   }
   return 0;
}

static void clearScreen() 
{
   System.out.println("\033[H\033[J");
   System.out.flush();
}


}

   
   

