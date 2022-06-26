package com.ja.currency_converter.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ja.currency_converter.converter.model.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Converter2 {

	private User[] users = new User[5];
	private User[] userz = new User[3];
	private List<String[]> list;
	private List<List<String>> fromList;
	private List<String> toList;
	
	private User[] array;

	/**
	 * reads transaction.txt splits each line individually & stores in local
	 * variable array initializes user object with name and map
	 * 
	 * @return {converter}
	 * @throws FileNotFoundException
	 * @throws JsonProcessingException
	 */
	public Converter2 readTextFile() {
		System.out.println("---reading from transaction.txt file---");
		String[] arr;
		int counter = 0;
		Scanner reader = null;
		try {
			reader = new Scanner(new FileReader("transactions.txt"));
			while (reader.hasNext()) {
				arr = reader.nextLine().split(" ");
				Map<String, Double> countries = new HashMap<String, Double>();
				User bob = new User(arr[0], countries);

				countries.put(arr[1], Double.parseDouble(arr[3]));
				countries.put(arr[2], Double.parseDouble(arr[3]));
				userz[++counter] = bob;
//				counter++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			reader.close();
		}

		return this;
	}

	/**
	 * helper method to print nested array in arraylist
	 */
	public void printArrListArr() {
		for (String[] arr : list) {
			System.out.println(Arrays.toString(arr));
		}
	}

	public Converter2 getFromCountry() {
		fromList = new ArrayList<>();
		for (int i = 0; i < this.list.size(); i++) {
			ArrayList<String> arrList = new ArrayList<>();
			for (int j = 0; j < this.list.get(i).length; j++) {
				arrList.add(list.get(i)[j]);
			}
			this.fromList.add(arrList);
		}
//		System.out.println("Printing fromList:\n" + fromList + "\nEnd fromList");
		return this;
	}

	public Converter2 getToCountry() {
		toList = new ArrayList<>();
		for (String[] arr : this.list) {
			for (int i = 0; i < arr.length; i++) {
				toList.add(arr[2]);
			}
		}
		return this;
	}

	/**
	 * iterates User[] instance variable and extracts User objects
	 * with same name into a set. The set is used to create an array
	 * of the countries. This countries array is then used to populate
	 * the map instance in the converged user object and extract the
	 * key value pairs. 
	 * @return {User}
	 */
	public Converter2 convergeDuplicatesUser() {
		Set<User> set = new HashSet<>();
		Set<User> nonDupSet = new HashSet<>();
//		User[] nonDuplicates = null;
		User user = new User();
		int counter = 0;
//		String dupName = null;
//		user.withName(users[0].getName());
//		user.getWallet().put("usd", Double.valueOf(users[0].getWallet().get("usd").toString()) * Double.valueOf((String) this.getJsonValues("usd").get("rate").toString()));
//		user.getWallet().put("cad", Double.valueOf(users[0].getWallet().get("cad").toString()) * Double.valueOf((String) this.getJsonValues("cad").get("rate").toString()));
		for (int i = 0; i < users.length; i++) {
			for (int j = i + 1; j < users.length; j++) {
				if (this.users[i].getName().equalsIgnoreCase(this.users[j].getName())) {
					counter++;
//					dupName = users[i].getName();
//					String name = this.users[i].getName();
//					user.setName(name);
//					user.getWallet().put(this.helperTextCountries().getFromCountry().getFromList().get(i).get(--j), this.users[i].getWallet().get(name));
//					user.getWallet().put(this.helperTextCountries().getToCountry().getToList().get(i), this.users[i].getWallet().get(name));
					// this.users[i].getWallet().put(this.users[i].getWallet().get(name),
					// this.users[i].getWallet().get(null));
					set.add(users[i]);
					set.add(users[j]);
				}else {
//					if(!(users[i].getName().equalsIgnoreCase("bob"))) {
//						nonDupSet.add(users[i]);
//					}
					nonDupSet.add(users[i]);//reverse
				}
			}
		}
		System.out.println("nonduplicates: " + nonDupSet.size());
		array = set.toArray(new User[set.size()]);
		user.withName(array[0].getName());
		
//		for(User userz : array) {
//			user.getWallet().put(userz., null)
//		}
		String[] strArr = null;
		int i = 0;
		
		for(User user1 : array) {
			strArr = user1.getWallet().keySet().toArray(new String[user1.getWallet().size()]);
			System.out.println(Arrays.toString(user1.getWallet().keySet().toArray(new String[user1.getWallet().size()])));
			
			for(int j = 0; j < strArr.length; j++) {
				user.getWallet().put(strArr[j], array[i].getWallet().get(strArr[j]) * Double.parseDouble(getJsonValues(strArr[j]).get("inverseRate").toString()));
			}
			if(i == strArr.length) {
				i = 0;
			}else {
				i++;
			}
		}
		
//		for(int i = 0; i < array.length; i++) {
//			for(int j = 0; j < strArr.length; j++) {
//				user.getWallet().put(strArr[j], array[i].getWallet().get(strArr[j]));
//			}
//		}
		
		//may consider returning this converged user object
//		this.users = convergeAllUsers(user, counter, nonDupSet.toArray(new User[nonDupSet.size() + 1]));
		this.users = convergeAllUsers(user, counter, nonDupSet.toArray(new User[3])); //reverse
//		System.out.println(counter);
		System.out.println("New User\n"+ user);
		return this;
	}
	
	private User[] convergeAllUsers(User user, int duplicates, User[] nonDuplicates) {
		//System.out.println("passed by value\n" + user);
//		int size = this.users.length;
//		this.users = new User[(size - duplicates) + 1];
		
		for(int i = 0; i < nonDuplicates.length; i++) {
			String[] nonDupArrKeys = 
					nonDuplicates[i].getWallet()
					.keySet().toArray(new String[nonDuplicates[i]
							.getWallet().size()]);
			for(int j = 0; j < nonDupArrKeys.length; j++) {
				nonDuplicates[i]
						.getWallet()
						.replace(nonDupArrKeys[j], 
								nonDuplicates[i].getWallet()
								.get(nonDupArrKeys[j]) * 
								Double.parseDouble(getJsonValues(nonDupArrKeys[j])
										.get("rate").toString()));
			}
		}
		
		for(int i = 0; i < nonDuplicates.length; i++) {
			if(nonDuplicates[i].getName().equalsIgnoreCase(user.getName())) { //reverse
				nonDuplicates[i] = user;
			}
//			if(nonDuplicates[i] == null) {
//				nonDuplicates[i] = user;
//			}
		}
		//System.out.println("new array values \n" + Arrays.toString(nonDuplicates));
		return nonDuplicates;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getJsonValues(String to) {
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map = null;

		try {
			map = mapper.readValue(new File("rates.json"), new TypeReference<Map<String, Object>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Map<String, Object> value = (Map<String, Object>)map.get(to);
		return (Map<String, Object>) map.get(to);
	}
	
	public Converter2 testMethod() {
		System.out.println(array);
		
		
		return this;
	}
	
	//== REFORMATION ==
	/**
	 * reads values from json file and stores in an User[]
	 * @return
	 * @throws IOException
	 */
	public Converter2 readFromUsersJSONFile () throws IOException {
		System.out.println("---reading from users.json file---\n");
		ObjectMapper mapper = new ObjectMapper();
		users = mapper.readValue(new File("users.json"), User[].class);
		return this;
	}
	
	/**
	 * read transactions.txt file splits each line's contents individually stores
	 * individual contents into an instance variable array
	 * 
	 * @return {converter}
	 * @throws FileNotFoundException
	 */
	public Converter2 helperTextCountries() {
		System.out.println("---printing transactions---");
		list = new ArrayList<>();
		Scanner reader = null;

		try {
			reader = new Scanner(new FileReader("transactions.txt"));
			while (reader.hasNext()) {
				String[] arr = new String[4];
				arr = reader.nextLine().split(" ");
				list.add(arr);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}

		for(String[] string : list) {
			System.out.println(Arrays.toString(string));
		}
		return this;
	}

	public Converter2 processTransactions() {
		System.out.println("---user.json values after the conversion---");
		for(String[] strArr : list) {
			for(User user : users) {
				if(!(strArr[1].equalsIgnoreCase(strArr[2]))) {
					if(user.getName().equalsIgnoreCase(strArr[0])) {
						if(strArr[1].equalsIgnoreCase("cad")) {
							if(user.getWallet().containsKey("cad")) {
								user.getWallet().replace("cad", user.getWallet().get("cad") - Double.parseDouble(strArr[3]));
								if(user.getWallet().containsKey(strArr[2])) {
									user.getWallet().replace(strArr[2], user.getWallet().get(strArr[2]) + Double.parseDouble(strArr[3]) * Double.parseDouble(getJsonValues(strArr[2]).get("rate").toString()));
								}else {
									user.getWallet().put(strArr[2], Double.parseDouble(strArr[3]) * Double.parseDouble(getJsonValues(strArr[2]).get("rate").toString()));
								}
							}
						}//else if(!strArr[1].equalsIgnoreCase("cad")) {
//							System.out.println(strArr[1]);
//							user.getWallet().replace(strArr[1], user.getWallet().get(strArr[1]) - Double.parseDouble(strArr[3]));
//							user.getWallet().replace(strArr[2], user.getWallet().get(strArr[2]) + Double.parseDouble(strArr[3]) * Double.parseDouble(getJsonValues(strArr[1]).get("inverseRate").toString()));
//						}
					}
				}
			}
		}
		
		for(int i = 3; i < list.size(); i++) {
			for(User user : users) {
				if(user.getName().equalsIgnoreCase(list.get(i)[0])) {
					if(!(user.getWallet().get(list.get(i)[1]) == null)) {
						user.getWallet().replace(list.get(i)[1], user.getWallet().get(list.get(i)[1]) - Double.parseDouble(list.get(i)[3]));
					}
					if(user.getWallet().get(list.get(i)[2]) == null) {
						if(!(user.getWallet().containsKey(list.get(i)[2]))) {
							//user.getWallet().put(list.get(i)[2], Double.parseDouble(list.get(i)[3]) * Double.parseDouble(getJsonValues(list.get(i)[2]).get("rate").toString()));
						}else {
//							user.getWallet().replace(list.get(i)[2], user.getWallet().get(list.get(i)[2]) + Double.parseDouble(list.get(i)[3]) * Double.parseDouble(getJsonValues(list.get(i)[1]).get("inverseRate").toString()));						
						}
					}
				}
			}
			
			for(int j = 3; j < list.size(); j++) {
				User user = getMatch(list.get(j)[0]);
//				user.getWallet().replace(list.get(j)[1], user.getWallet().get(list.get(j)[1]) - Double.parseDouble(list.get(j)[3]));

				if(user != null) {
//					if(user.getWallet().containsKey(list.get(j)[1])) {
//						user.getWallet().replace(list.get(j)[1], user.getWallet().get(list.get(j)[1]) - Double.parseDouble(list.get(j)[3]));
//					}else {
//						user.getWallet().put(list.get(j)[1], Double.parseDouble(list.get(j)[3]) * Double.parseDouble(getJsonValues(list.get(j)[1]).get("rates").toString()));
//					}
					if(!user.getWallet().containsKey(list.get(j)[2])) {
//						user.getWallet().replace(list.get(j)[2], user.getWallet().get(list.get(j)[2]) + Double.parseDouble(list.get(j)[3]));
//						//wk
						user.getWallet().put(list.get(j)[2], (Double.parseDouble(list.get(j)[3]) * Double.parseDouble(getJsonValues(list.get(j)[1]).get("inverseRate").toString())) * Double.parseDouble(getJsonValues(list.get(j)[2]).get("rate").toString()));
					}else if(user.getWallet().containsKey(list.get(j)[2])) {
//						user.getWallet().put(list.get(j)[2], Double.parseDouble(list.get(j)[3]) * Double.parseDouble(getJsonValues(list.get(j)[1]).get("inverseRate").toString()) * Double.parseDouble(getJsonValues(list.get(j)[2]).get("rate").toString()));
//						user.getWallet().replace(list.get(j)[2], user.getWallet().get(list.get(j)[2]) + Double.parseDouble(list.get(j)[3]));
					}
				}
//				System.out.println("tempTo " + tempTo);
			}
		}
		
		return this;
	}
	
	public User getMatch(String name) {
		for(User user : users) {
			if(user.getName().equalsIgnoreCase(name)) {
				return user;
			}
		}
		return null;
	}
	
//	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		Converter2 convert = new Converter2();		
		ObjectMapper mapper = new ObjectMapper();
//		User[] users = mapper.readValue(new File("users.json"), User[].class);
//		
//		System.out.println(Arrays.toString(users));

//		String[] arr;
//		Scanner reader = new Scanner(new FileReader("transactions.txt"));
//		while(reader.hasNext()) {
//			arr = reader.nextLine().split(" ");
//			Map<String, Double> countries = new HashMap<String, Double>();
//			User bob = new User(arr[0], countries);
//
//			countries.put(arr[1], Double.parseDouble(arr[3]));
//			countries.put(arr[2], Double.parseDouble(arr[3]));
//			
//			//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bob));
//		}

//		Map<String, Object> map = mapper.readValue(new File("rates.json"), new TypeReference<Map<String, Object>>(){});
//
//		Map<String, Object> value = (Map<String, Object>)map.get("usd");
//		System.out.println(value.get("rate"));
		// JSONObject jsonObj = new JSONObject(map.get("usd"));
//		System.out.println(jsonObj.get("usd"));
		// String result = (String) convert.readTextFile()[2];
//		System.out.println(convert.getJsonValues("cad").get("rate"));
		// System.out.println(convert.readTextFile());
		//convert.readTextFile().helperTextCountries().convergeDuplicatesUser();
//		for(User user : convert.getUsers()) {
//		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
//						convert.readTextFile().helperTextCountries().convergeDuplicatesUser().getUsers()));
////		}
//		System.out.println("Printing User\n");
////		convert.testMethod()[0].toString();
//		
//		System.out.println(Arrays.toString(convert.readFromUsersJSONFile()));
		
		//== printing users.json file
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(convert.readFromUsersJSONFile().getUsers()));

//		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(convert.readTextFile().getUserz()));
		
		//== printing transaction.txt file
		convert.helperTextCountries();
		
		//== reading and printing user.json after processing transaction
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(convert.processTransactions().getUsers()));
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("updatedUsers.json"), convert.getUsers());
	}
}
