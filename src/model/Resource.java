package model;

import java.util.ArrayList;


public class Resource {
	private ArrayList<Account> accs;
	
	public Resource(){
		accs= new ArrayList();
		User user = new User("andrey","123");
		Account acc = new Account();
		acc.setUser(user);
		accs.add(acc);
		user = new User("asen","asen");
		acc = new Account();
		acc.setUser(user);
		accs.add(acc);
		
		
	}
	public void addAccount(Account acc)
	{
		accs.add(acc);
	}
	
	public boolean isValidUser(User user)
	{
		boolean is = false;
		for(int i=0;i<accs.size();i++){
			if( (accs.get(i).getUser().getId().equals(user.getId()))  && (accs.get(i).getUser().getPass().equals(user.getPass() )) ){
				is = true;
				break;
			}
		}
		return is;
	}
	
	public boolean setDataForUser (User user, String data)
	{
		boolean status = false;
		for(int i=0;i<accs.size();i++){
			if(accs.get(i).getUser().getId().equals(user.getId()) && accs.get(i).getUser().getPass().equals(user.getPass())){
				accs.get(i).setData(data);
				status = true;
				break;
			}
		}
		return status;
	}
	
	public String getDataForUser(User user)
	{
		String data = "";
		for(int i=0;i<accs.size();i++){
			if(accs.get(i).getUser().getId().equals(user.getId()) && accs.get(i).getUser().getPass().equals(user.getPass())){
				data = accs.get(i).getData();
				break;
			}
		}
		return data;
	}
}
