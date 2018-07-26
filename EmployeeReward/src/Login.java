import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Login {
	static InputStreamReader isr=new InputStreamReader(System.in);
	static BufferedReader br=new BufferedReader(isr);
	static Connection conn=null;
	static Statement stmt = null;
	static Statement stmt1 = null;
	static int Userid = 0;
	public static Connection getDbConnection()  
	{
		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/EmpRewardSystem", "postgres", "12345");
		} catch (Exception e) {
			System.out.println("Connection Failed");
			e.printStackTrace();
		}
		return conn;
	}
	
	private static void getLogin() {
		// TODO Auto-generated method stub
		try {
		if(conn!=null) {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t### Employee Reward System ###");
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\tUser Login");
			
			System.out.println();
			System.out.print("\t\t\t\t\t\t\t\tUserID (or EmailID) : ");
			String UserID = br.readLine();
			System.out.print("\t\t\t\t\t\t\t\tPassword : ");
			String Password = br.readLine();
			stmt=conn.createStatement();  
			//ResultSet count=stmt.executeQuery("select count(empid) from tblemployee where (euserid='"+ UserID +"' or emailid = '"+ UserID +"') and password = '"+ Password +"'");
			//if(count!=null) {
			ResultSet rs1 = stmt.executeQuery("select * from tblemployee where (euserid='"+ UserID +"' or emailid = '"+ UserID +"') and password = '"+ Password +"' and isactive='1'");
			if(rs1!=null) {
				
				if(rs1.next()) {
					ResultSet rs=stmt.executeQuery("select * from tblemployee where (euserid='"+ UserID +"' or emailid = '"+ UserID +"') and password = '"+ Password +"' and isactive='1'");
					
					System.out.println();
					System.out.println();
					while(rs.next()) { 
						System.out.println("Welcome " + rs.getString(2) + " " + rs.getString(3) + "!");
						Userid = rs.getInt(1);
					}
					EmployeePenal();
				}
				else {
					ResultSet rs2 = stmt.executeQuery("select * from tbladmin where userid='"+ UserID +"' and passsword = '"+ Password +"' and isactive='1'");
					if(rs2!=null) {
						
						if(rs2.next()) {
							ResultSet rs=stmt.executeQuery("select * from tbladmin where userid='"+ UserID +"' and passsword = '"+ Password +"' and isactive='1'");
							System.out.println();
							System.out.println();
							while(rs.next()) {
								System.out.println("Welcome " + rs.getString(2) + " " + rs.getString(3) + "!");
								Userid = rs.getInt(1);
							}
							AdminPenal();
						}
						else {
							System.out.println("UserID or EmailID or Password are Wrong! Please try Again.");
						}
					}
				}
			}  
		}
		else
			System.out.println("Connection Failed");
		}catch (Exception e) {
			//System.out.println("Connection Failed");
			e.printStackTrace();
			
		}
	}
	
	
	
	private static void AdminPenal() throws IOException {
		System.out.println();
		int Choice = 0;
		do {
			System.out.print("1.View Admin\n2.View Employee\n3.Add Department(Role and type)\n4.Genrate Reward\n5.View Performance\n6.View Reward\n7.Add Employee\n8.Update Employee Data\n9.Logout\n\nEnter Your Choice : ");
			Choice = Integer.parseInt(br.readLine());
			switch(Choice) {
			case 1:
				ViewAdmin();
				break;
			case 2:
				ViewEmployee();
				break;
			case 3:
				AddDepartment();
				break;
			case 4:
				GenrateReward();
				break;
			case 5:
				ViewPerformance();
				break;
			case 6:
				ViewReward();
				break;
			case 7:
				UpdateEmp();
				break;
			case 8:
				AddEmployee();
				break;
			case 9:
				Logout();
				break;
			default:
				System.out.println("Enter Proper Choice. Please Try Again!");
				break;
			}
		}while(Choice!=8);
		
	}

	
	private static void AddEmployee() {
		// TODO Auto-generated method stub
		try {
            System.out.print("Enter First Name : ");
            String First_Name = br.readLine();
            System.out.print("Enter Last Name : ");
            String Last_Name = br.readLine();
            System.out.print("Enter Employee UserId : ");
            String UserId = br.readLine();
            System.out.print("Enter Password : ");
            Integer Password = Integer.parseInt(br.readLine());
            System.out.print("Enter Employee EmailId : ");
            String EmailId = br.readLine();
            System.out.print("Enter Gender : ");
            Integer Gender = Integer.parseInt(br.readLine());
            System.out.print("Enter ConatctNo : ");
            String ContactNo = br.readLine();
            System.out.print("Enter Address : ");
            String Address = br.readLine();
            stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select * from department.tbldepartment");
            while(rs1.next())
                    System.out.println(rs1.getInt(1) + "\t" + rs1.getString(2));
            System.out.print("Enter Department : ");
            int Dept=Integer.parseInt(br.readLine());
            stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery("select * from department.tbldepartmentrole");
            while(rs2.next())
                    System.out.println(rs2.getInt(1) + "\t" + rs2.getString(2));
            System.out.print("Enter Role Type : ");
            int Role=Integer.parseInt(br.readLine());
            String query1="Select departmentxroleid from department.tbldepartmentxrole  where departmentid="+ Dept +"  and departmentroleid="+ Role +" ";
            stmt=conn.createStatement();
            ResultSet rs = stmt.executeQuery(query1);
            rs.next();
            System.out.print("Enter Insert Permission : ");
            int IsInsert = Integer.parseInt(br.readLine());
            System.out.print("Enter Update Permission : ");
            int IsUpdate = Integer.parseInt(br.readLine());
            System.out.print("Enter Delete Permission : ");
            int IsDelete = Integer.parseInt(br.readLine());
            System.out.print("Enter Activation Status : ");
            int IsActive = Integer.parseInt(br.readLine());
            String demo ="INSERT INTO public.tblemployee(firstname, lastname, euserid, password, emailid, gender, contactno, address, departmentxroleid, isinsert, isupdate, isdelete, isactive, createdby, createdon) VALUES ('"+First_Name+"','"+Last_Name+"' , '"+UserId+"', '"+Password+"', '"+EmailId+"', "+Gender+", '"+ContactNo+"', '"+Address+"', "+rs.getInt(1)+", '"+IsInsert+"', '"+IsUpdate+"', '"+IsDelete+"', '"+IsActive+"',"+Userid+",current_date);";
            //String query = "INSERT INTO public.tblemployee(firstname, lastname, euserid, password, emailid, gender, contactno, address, departmentxroleid, isinsert, isupdate, isdelete, isactive, createdby, createdon) VALUES ('"+ First_Name +"','"+ Last_Name +"' , '"+ UserId +"', '"+Password+"', '"+EmailId+"', "+Gender+", '"+ContactNo+"', '"+Address+"', "+ rs.getInt(1) +", '"+IsInsert+"', '"+IsUpdate+"', '"+IsDelete+"', '"+IsActive+"',"+Userid+",current_date);";
            PreparedStatement preparedStatement = conn.prepareStatement(demo);
            System.out.println(demo);
            preparedStatement.execute();
		}catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
		}
	}

	private static void UpdateEmp() {
		// TODO Auto-generated method stub
		try {
			ViewEmployee();
			System.out.print("Enter Employee Id : ");
			int EId = Integer.parseInt(br.readLine());
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from tblemployee where empid="+EId);
			if(rs.next()) {
				stmt1=conn.createStatement();
				ResultSet rs3=stmt1.executeQuery("select * from department.tbldepartmentxrole where departmentxroleid="+rs.getInt(10));
				rs3.next();
				ResultSet rs1 = stmt1.executeQuery("select * from department.tbldepartment");
				while(rs1.next())
					System.out.println(rs1.getInt(1) + "\t" + rs1.getString(2));
				System.out.print("Enter Department : ");
				int Dept=Integer.parseInt(br.readLine());
				if(Dept==0)
					Dept=rs3.getInt(3);
				ResultSet rs2 = stmt1.executeQuery("select * from department.tbldepartmentrole");
				while(rs2.next())
					System.out.println(rs2.getInt(1) + "\t" + rs2.getString(2));
				System.out.print("Enter Role Type : ");
				int Role=Integer.parseInt(br.readLine());
				if(Role==0)
					Role=rs3.getInt(2);
				System.out.print("Enter Insert Permission (Current Permission : "+rs.getBoolean(11)+") : ");
				Boolean insert=Boolean.parseBoolean(br.readLine());
				System.out.print("Enter Update Permission (Current Permission : "+rs.getBoolean(12)+": ");
				Boolean update=Boolean.parseBoolean(br.readLine());
				System.out.print("Enter Delete Permission (Current Permission : "+rs.getBoolean(13)+": ");
				Boolean delete=Boolean.parseBoolean(br.readLine());
				System.out.print("Enter Activation Status (Current Status : "+rs.getBoolean(14)+": ");
				Boolean active=Boolean.parseBoolean(br.readLine());
				//String demo = "select public.updateemployeebyown("+ fname +", "+ lname +","+ userid +", "+ pwd +","+ email +", "+ gender +","+ cno +","+ address +","+ Userid +")";
				String query = "select public.updateemployeebyadmin("+Dept+","+Role+", '"+insert+"','"+update+"','"+delete+"','"+active+"',"+Userid+");";
				CallableStatement cStmt = conn.prepareCall(query);
				boolean hadResults = cStmt.execute();
				if(hadResults) {
					System.out.println("\nSuccess");
				}
				else {
					System.out.println("\nFailure");
				}
			}
			else {
				System.out.println("Employee Not Found.");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void ViewAdmin() {
		// TODO Auto-generated method stub
		try {
			ResultSet rs=stmt.executeQuery("select * from tblAdmin where adminid != " + Userid);
			System.out.println("\n\nName\t\t\tContect Number\t\tIsActive\tIsSuper\t\tCreatedBy\n---------------------------------------------------------------------------------------------");
			while(rs.next()) {
				System.out.println(rs.getString(2) + " " + rs.getString(3) + "\t\t" + rs.getString(6) + "\t\t" + "\t" + rs.getString(7) + "\t" + rs.getBoolean(8) + "\t\t" + rs.getBoolean(9));
			}
			System.out.print("\n\n");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	private static void ViewEmployee() {
		// TODO Auto-generated method stub
		try {
			ResultSet rs=stmt.executeQuery("select * from tblEmployee");
			while(rs.next()) { 
				String Gender = null;
				if(rs.getInt(7)==0) {
					Gender = "Male";
				}else if(rs.getInt(7)==1) {
					Gender = "Female";
				}
				else {
					Gender = "Other";
				}
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + " " + rs.getString(3) + "\t\t" + rs.getString(6) + "\t\t" + Gender + "\t" + rs.getString(8) + "\t" + rs.getBoolean(11) + "\t" + rs.getBoolean(12) + "\t" + rs.getBoolean(13) + "\t" + rs.getBoolean(14) + "\t" + rs.getInt(15) + "\t" + rs.getDate(16));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void AddDepartment() throws IOException {
		// TODO Auto-generated method stub
		System.out.println();
		int Choice = 0;
		do {
			System.out.print("1.Add Department\n2.Add Roles\n3.Add Role Of Department\n4.Back\n\nEnter Your Choice : ");
			Choice = Integer.parseInt(br.readLine());
			switch(Choice) {
			case 1:
				AddDepartments();
				break;
			case 2:
				AddRoles();
				break;
			case 3:
				AddRoleOfDepartment();
				break;
			case 4:
				break;
			default:
				System.out.println("Enter Proper Choice. Please Try Again!");
				break;
			}
		}while(Choice!=4);
		
	}

	private static void AddDepartments() throws IOException {
		// TODO Auto-generated method stub
		try {
			System.out.print("Enter Department Name : ");
			String DeptName = br.readLine();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from department.tbldepartment where departmentname='" + DeptName + "'");
			if(rs.next()) {
				System.out.print("Department Already Exist.");
			}
			else {
				LocalDate localDate = LocalDate.now();
				String query = "INSERT INTO department.tbldepartment(departmentname, createdby, createdon) VALUES ('" + DeptName + "', " + Userid + ", '" + DateTimeFormatter.ofPattern("MM-dd-YYYY").format(localDate) + "');";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void AddRoles() throws IOException {
		// TODO Auto-generated method stub
		try {
			System.out.print("Enter Department Name : ");
			String DeptName = br.readLine();
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from department.tbldepartmentrole where roletype='" + DeptName + "'");
			if(rs.next()) {
				System.out.print("Department Already Exist.");
			}
			else {
				System.out.print("Enter Priority Of Department : ");
				int Priority = Integer.parseInt(br.readLine());
				stmt = conn.createStatement();
				LocalDate localDate = LocalDate.now();
				String query = "INSERT INTO department.tbldepartmentrole(roletype, createdby, createdon, priority) VALUES ('" + DeptName + "', " + Userid + ", '" + DateTimeFormatter.ofPattern("MM-dd-YYYY").format(localDate) + "', " + Priority + ");";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void AddRoleOfDepartment() throws IOException {
		// TODO Auto-generated method stub
		try {
			
			System.out.print("Enter Department Number : ");
			int Deptid = Integer.parseInt(br.readLine());
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from department.tbldepartment where departmentid=" + Deptid);
			if(rs.next()) {
				System.out.print("Enter Role Type Number : ");
				int Roleid = Integer.parseInt(br.readLine());
				ResultSet rs1 = stmt.executeQuery("select * from department.tbldepartmentrole where departmentroleid=" + Roleid);
				if(rs1.next()) {
					stmt1 = conn.createStatement();
					ResultSet rs2 = stmt1.executeQuery("select * from department.tbldepartmentxrole where departmentroleid=" + Roleid + " and  departmentid=" + Deptid);
					if(rs2.next()) {
						System.out.print("Role Already Exist.");
					}
					else {
						LocalDate localDate = LocalDate.now();
						String query = "INSERT INTO department.tbldepartmentxrole(departmentroleid, departmentid, createdby, createdon) VALUES (" + Roleid + ", " + Deptid + "," + Userid + ", '" + DateTimeFormatter.ofPattern("MM-dd-YYYY").format(localDate) + "');";
						PreparedStatement preparedStmt = conn.prepareStatement(query);
						preparedStmt.execute();
					}
				}
				else {
					System.out.print("Role Not Exist.");
				}
			}
			else {
				System.out.print("Department Not Exist.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void GenrateReward() {
		// TODO Auto-generated method stub
		try {
			Calendar now = Calendar.getInstance();
			System.out.println(now.get(Calendar.MONTH) + 1);
			System.out.println(now.get(Calendar.DATE));
			if(((now.get(Calendar.MONTH) + 1) == 6 || (now.get(Calendar.MONTH) + 1) == 12) && (now.get(Calendar.DATE) > 25 && now.get(Calendar.DATE) <= 31)) {
				ResultSet rs=stmt.executeQuery("select * from tblemployee where IsActive = '1' and empid='1' ");
				//130
//				Double Skill = 0.0;
//				Double Quality = 0.0;
//				Double ClientFeedback = 0.0;
				
				while(rs.next()) {
					String query = "INSERT INTO tblreward(empid, rewardpoint, createdby, createdon, rewardtype) select E.empid as empid,ROUND(((Sum(P.skills)+Sum(P.quality)+Sum(P.clientfeedback))*100)/390,1) as rewardpoint,1 as createdby,to_date(to_char( now(), 'MM-DD-YYYY' ),'MM-DD-YYYY') as createdon,'1000 Rs Plus' as rewardtype from tblemployee E, tblperformance P where E.empid=P.empid and E.empid = " + rs.getInt(1) + " group by E.empid;";
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.execute();
					
//					stmt1 = conn.createStatement();
//					ResultSet rs1=stmt1.executeQuery("select * from tblperformance where empid = "+rs.getInt(1));		
//					while(rs1.next()) {
//						Skill += rs1.getDouble(3);
//						Quality += rs1.getDouble(4);
//						ClientFeedback += rs1.getDouble(5);
//					}
//					stmt1.executeQuery("INSERT INTO public.tblreward(empid, rewardpoint, createdby, createdon, rewardtype) VALUES ("+rs.getInt(1)+"," +  + ", ?, ?, ?);");
				}
			}
			else {
				System.out.print("ops! ");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void ViewReward() {
		// TODO Auto-generated method stub
		try {
			ResultSet rs=stmt.executeQuery("select * from tblReward");
			while(rs.next()) { 
				Double P1 = rs.getDouble(3);
				System.out.println(rs.getInt(2) + "\t" + P1 + "\t" + rs.getInt(4) + "\t" + rs.getDate(5) + "\t" + rs.getString(6));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void ViewPerformance() {
		// TODO Auto-generated method stub
		try {
			ResultSet rs=stmt.executeQuery("select * from tblPerformance");
			while(rs.next()) { 
				Double P1 = rs.getDouble(3); 
				Double P2 = rs.getDouble(4);
				Double P3 = rs.getDouble(5);
				System.out.println(rs.getInt(2) + "\t" + P1 + "\t" + P2 + "\t" + P3 + "\t" + rs.getInt(6) + "\t" + rs.getDate(7));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	

	private static void EmployeePenal() throws IOException {
		try {
			stmt = conn.createStatement();
			int Choice = 0;
			ResultSet rs=stmt.executeQuery("select R.priority from tblemployee E, department.tbldepartmentxrole XR, department.tbldepartmentrole R where E.departmentxroleid=XR.departmentxroleid and R.departmentroleid=XR.departmentroleid and E.empid = " + Userid);
			if(rs.next()) {
				stmt1 = conn.createStatement();
				ResultSet rs1=stmt1.executeQuery("select max(priority) from department.tbldepartmentrole");
				if(rs1.next()) {
					if(rs1.getInt(1)==rs.getInt(1)) {

						do {
							System.out.print("1.View Profile\n2.Edit Profile\n3.Logout\n\nEnter Your Choice : ");
							Choice = Integer.parseInt(br.readLine());
							switch(Choice) {
							case 1:
								view_profile();
								view_performance(Userid);
								view_reward();
								break;
							case 2:
								EditProfileByOwn();
								break;
							case 3:
								Logout();
								break;
							default:
								System.out.println("Enter Proper Choice. Please Try Again!");
								break;
							}
						}while(Choice!=3);
					}else if(rs.getInt(1)==2) {
						do {
							System.out.print("1.View Profile\n2.Edit Profile\n3.View Subordinates\n4.Give Performance\n5.Logout\n\nEnter Your Choice : ");
							Choice = Integer.parseInt(br.readLine());
							switch(Choice) {
							case 1:
								view_profile();
								view_performance(Userid);
								view_reward();
								break;
							case 2:
								EditProfileByOwn();
								break;
							case 3:
								ViewLevel();
								break;
							case 4:
								ViewLevel();
								GivePerformance();
								break;
							case 5:
								Logout();
								break;
							default:
								System.out.println("Enter Proper Choice. Please Try Again!");
								break;
							}
						}while(Choice!=5);
					}else if(rs.getInt(1)==1){
						do {
							System.out.print("1.View Profile\n2.Edit Profile\n3.View Employee\n4.Give Performance\n5.Logout\n\nEnter Your Choice : ");
							Choice = Integer.parseInt(br.readLine());
							switch(Choice) {
							case 1:
								view_profile();
								view_performance(Userid);
								view_reward();
								break;
							case 2:
								EditProfileByOwn();
								break;
							case 3:
								ViewEmployee();
								break;
							case 4:
								ViewLevel();
								GivePerformance();
								break;
							case 5:
								Logout();
								break;
							default:
								System.out.println("Enter Proper Choice. Please Try Again!");
								break;
							}
						}while(Choice!=5);
					}else {
						do {
							System.out.print("1.View Profile\n2.Edit Profile\n3.View Subordinates\n4.Logout\n\nEnter Your Choice : ");
							Choice = Integer.parseInt(br.readLine());
							switch(Choice) {
							case 1:
								view_profile();
								view_performance(Userid);
								view_reward();
								break;
							case 2:
								EditProfileByOwn();
								break;
							case 3:
								ViewLevel();
								break;
							case 4:
								Logout();
								break;
							default:
								System.out.println("Enter Proper Choice. Please Try Again!");
								break;
							}
						}while(Choice!=4);
					}
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
//		System.out.println();
//		System.out.println("1.View Profile\n2.View Performance\n3.View Reward\n"); 
//		view_profile();
//		view_performance();
//		view_reward();
//		ViewLevel();
	}
	
	private static void EditProfileByOwn() throws IOException {
		// TODO Auto-generated method stub
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from tblemployee where empid="+Userid);
			rs.next();
			System.out.print("Enter First Name : ");
			String fname=br.readLine();
			if(fname.equals("#"))
				fname=rs.getString(2);
			System.out.print("Enter Last Name : ");
			String lname=br.readLine();
			if(lname.equals("#"))
				lname=rs.getString(3);
			System.out.print("Enter UserId : ");
			String userid=br.readLine();
			if(userid.equals("#"))
				userid=rs.getString(4);
			System.out.print("Enter Password : ");
			String pwd=br.readLine();
			if(pwd.equals("#"))
				pwd=rs.getString(5);
			System.out.print("Enter Email : ");
			String email=br.readLine();
			if(email.equals("#"))
				email=rs.getString(6);
			System.out.print("Enter Gender(1 For Male, 2 For Female, 3 For Other) : ");
			int gender=Integer.parseInt(br.readLine());
			if(gender==0)
				gender=rs.getInt(7);
			else
				gender--;
			System.out.print("Enter Contect Number : ");
			String cno=br.readLine();
			if(cno.equals("#"))
				cno=rs.getString(8);
			System.out.print("Enter Address : ");
			String address=br.readLine();
			if(address.equals("#"))
				address=rs.getString(9);
			//String demo = "select public.updateemployeebyown("+ fname +", "+ lname +","+ userid +", "+ pwd +","+ email +", "+ gender +","+ cno +","+ address +","+ Userid +")";
			String query = "select public.updateemployeebyown('"+fname+"','"+lname+"', '"+userid+"','"+pwd+"','"+email+"',"+gender+",'"+cno+"','"+address+"',"+Userid+");";
			CallableStatement cStmt = conn.prepareCall(query);
			boolean hadResults = cStmt.execute();
			if(hadResults) {
				System.out.println("\nSuccess");
			}
			else {
				System.out.println("\nFailure");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void GivePerformance() throws IOException {
		// TODO Auto-generated method stub
		try {
			System.out.print("Enter Employee Id : ");
			int EmpID = Integer.parseInt(br.readLine());
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from tblemployee where empid=" + EmpID);
			if(rs.next()) {
				int Choice;
				do {
					System.out.print("1.Add Performance\n2.Update Performance\n3.back\n\nEnter Your Choice : ");
					Choice = Integer.parseInt(br.readLine());
					switch(Choice) {
					case 1:
						AddPerformance(EmpID);
						break;
					case 2:
						view_performance(EmpID);
						UpdatePerformance();
						break;
					case 3:						
						break;
					default:
						System.out.println("Enter Proper Choice. Please Try Again!");
						break;
					}
				}while(Choice!=3);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void UpdatePerformance() throws IOException {
		// TODO Auto-generated method stub
		try {
			System.out.print("Enter Performance Id : ");
			int PId = Integer.parseInt(br.readLine());
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from tblperformance where performanceid = "+ PId);
			if(rs.next()) {
				System.out.print("Enter Skill Point(If Not Want To Change Then Enter 0) : ");
				Double Skill = Double.parseDouble(br.readLine());
				System.out.print("Enter Quality Point(If Not Want To Change Then Enter 0) : ");
				Double Quality = Double.parseDouble(br.readLine());
				System.out.print("Enter Click Feedback Point(If Not Want To Change Then Enter 0) : ");
				Double ClickFeedback = Double.parseDouble(br.readLine());
				if(Skill==0) {
					Skill = rs.getDouble(3);
				}
				if(Quality==0) {
					Quality = rs.getDouble(4);
				}
				if(ClickFeedback==0) {
					ClickFeedback = rs.getDouble(5);
				}
				String query = "UPDATE public.tblperformance SET skills="+Skill+", quality="+Quality+", clientfeedback="+ClickFeedback+" WHERE performanceid="+PId;
				PreparedStatement preparedStatement = conn.prepareStatement(query);
				preparedStatement.execute();
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void AddPerformance(int EmpID) throws IOException {
		// TODO Auto-generated method stub
		try {
			System.out.print("Enter Skill Point : ");
			Double Skill = Double.parseDouble(br.readLine());
			System.out.print("Enter Quality Point : ");
			Double Quality = Double.parseDouble(br.readLine());
			System.out.print("Enter Click Feedback Point : ");
			Double ClickFeedback = Double.parseDouble(br.readLine());
			String query = "INSERT INTO public.tblperformance(empid, skills, quality, clientfeedback, createdby, createdon) VALUES (" + EmpID + ", " + Skill + ", " + Quality + "," + ClickFeedback + "," + Userid + ",current_date)";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.execute();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void ViewLevel() {
        // TODO Auto-generated method stub
        
        try {
                ResultSet rs=stmt.executeQuery("select E.* from tblemployee E, tblemployee E1, department.tbldepartmentxrole XR, department.tbldepartmentxrole XR1, department.tbldepartmentrole R, department.tbldepartmentrole R1, department.tbldepartment D, department.tbldepartment D1 where E.departmentxroleid = XR.departmentxroleid and R.departmentroleid=XR.departmentroleid and D.departmentid=XR.departmentid and E1.departmentxroleid = XR1.departmentxroleid and R1.departmentroleid=XR1.departmentroleid and D1.departmentid=XR1.departmentid and D.departmentid = D1.departmentid and R.priority > R1.priority and E1.empId=" + Userid + ";");
                while(rs.next()) { 
                        String Gender = null;
                        if(rs.getInt(7)==0) {
                                Gender = "Male";
                        }else if(rs.getInt(7)==1) {
                                Gender = "Female";
                        }
                        else {
                                Gender = "Other";
                        }
                        System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + " " + rs.getString(3) + "\t\t" + rs.getString(6) + "\t\t" + Gender + "\t" + rs.getString(8) + "\t" + rs.getBoolean(11) + "\t" + rs.getBoolean(12) + "\t" + rs.getBoolean(13) + "\t" + rs.getBoolean(14) + "\t" + rs.getInt(15) + "\t" + rs.getDate(16));
                }
        }catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
        }
	}
	
	private static void view_reward() {
		try {
	    	 stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery( "SELECT * FROM tblreward where empid=" + Userid + ";" );
	         System.out.println("\nReward Detail(Duration of 6 Months)\n");
	         while ( rs.next() ) {
	            Double  rewardpoint = rs.getDouble("rewardpoint");
	            System.out.println( "Reward Points : " + rewardpoint + "\tReward Type : " + rs.getString("rewardtype") );
	            System.out.println();
	         }
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	      }
	}

	private static void view_performance(int user) {
		int i=1;
		try {
	    	 stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery( "SELECT * FROM tblperformance where empid=" + user + ";" );
	         System.out.println("\n--------------- Weekly Performance ---------------\n");
	         while ( rs.next() ) {
	            Double  skills = rs.getDouble("skills");
	            Double quality  = rs.getDouble("quality");
	            Double  feedback = rs.getDouble("clientfeedback");
	            System.out.println(rs.getInt(1) + "\tWeek " + i++ + " (" +rs.getDate(7) + ")\tSkill : " + skills + "\tQuality : " + quality + "\tClient Feedback : " + feedback );
	         }
	        
	         
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	      }
		
	}

	private static void view_profile() {
		 try {
	    	 stmt = conn.createStatement();
	    	 stmt1 = conn.createStatement();
	         ResultSet rs = stmt.executeQuery( "SELECT * FROM tblemployee where empid=" + Userid + ";" );
	         System.out.println("\n************ Profile ************\n");
	         while ( rs.next() ) {
	            int gender = rs.getInt("gender");
	            String Gender = null;
	            if(gender==0)
	            	Gender="Male";
	            else if(gender==1)	            
	            	Gender="Female";
	            else
	            	Gender="Other";	           
	            System.out.println( "Name : " + rs.getString("firstname") + " " + rs.getString("lastname"));
	            
	            System.out.println("Gender : " + Gender );
	            System.out.println( "\nContect Detail\n--------------\nEmailID : " + rs.getString("emailid") + "\tContact No : " + rs.getString("contactno"));
	            System.out.println( "Address : " + rs.getString("address"));
	            int ID = rs.getInt("departmentxroleid");
	           	ResultSet rs1 = stmt1.executeQuery("select departmentname,roletype from department.tbldepartment D,department.tbldepartmentxrole X, department.tbldepartmentrole R  where X.departmentid=D.departmentid and X.departmentroleid=R.departmentroleid and X.departmentxroleid=" + ID);
	           	while ( rs1.next() ) {
	            	System.out.println("\nDepartment Detail\n-----------------\nDepartment Name : " + rs1.getString("departmentname") + "\tDepartment Role : " + rs1.getString("roletype") );
	            }
	            System.out.println();
	         }
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage());
	         System.exit(0);
	      }
		
	}


	private static void Logout() {
		// TODO Auto-generated method stub
		try {
			conn.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	public static void main(String[] args) throws IOException {		
		getDbConnection();
		if(conn!=null)
			getLogin();
	}	
}
