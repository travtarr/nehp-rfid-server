package com.nehp.rfid_system;

import static org.fest.assertions.Assertions.assertThat;
import io.dropwizard.testing.junit.DropwizardAppRule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;

import org.junit.ClassRule;
import org.junit.Test;

import com.google.common.io.Resources;
import com.nehp.rfid_system.server.MainApp;
import com.nehp.rfid_system.server.MainConfiguration;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.core.ItemList;
import com.nehp.rfid_system.server.core.User;
import com.nehp.rfid_system.server.core.UserWrap;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;


public class ResourcesTest {
	
	private static final long USERID = 2L;

	@ClassRule
	public static final DropwizardAppRule<MainConfiguration> RULE;
	
	static {
		try {
			RULE = new DropwizardAppRule<>(MainApp.class, new File(Resources.getResource("config.yml").toURI()).getAbsolutePath());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	TestHelpers helper = new TestHelpers(RULE);
	
	@Test
	public void getUserReturns200AndUserObject() {
		ClientResponse response = helper.get("/users/" + USERID, helper.accessToken());		
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
	}
	
	@Test
	public void getUserNoTokenReturns401() {
		ClientResponse response = helper.get("/users/" + USERID);		
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
	}
	
	/*@Test
	public void updateUserInfoReturns200(){
		String user = helper.get("/users/" + USERID, helper.accessToken()).getEntity(String.class);
		ClientResponse response = helper.put("/users/" + USERID, helper.accessToken(), user);
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
	}*/
	
	@Test
	public void noficationsGetAllReturnsNotificationsAndStatus200(){
		ClientResponse response = helper.get("/notifications", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void noficationsGetAllBadTokenReturnsStatus401(){
		ClientResponse response = helper.get("/notifications", "asas");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void noficationsGetAllNoTokenReturnsStatus401(){
		ClientResponse response = helper.get("/notifications");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void notificationsGetByIdReturnsNotificationsAndStatus200(){
		ClientResponse response = helper.get("/notifications/1", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void notificationsGetByIdBadTokenReturnsStatus401(){
		ClientResponse response = helper.get("/notifications/1", "ddsdfs");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.UNAUTHORIZED.getStatusCode());
	}
	
	@Test
	public void itemsGetByTypeALLReturnsItemsAndStatus200(){
		ClientResponse response = helper.get("/items", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void settingsGetByIdReturnsStatus200(){
		ClientResponse response = helper.get("/settings/2", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
//	public void changePWReturns200(){
//		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
//		formData.add("grant_type", "password");
//		formData.add("username", "alpha");
//		formData.add("password", "alpha");
//		ClientResponse response = helper.post("/users/pwchange", helper.accessToken(), formData);	
//		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
//	}
	
	@Test
	public void downloadFile() {
		ClientResponse response = helper.get("/download", helper.accessToken("password", "downloader", "zV7Qbek0TWYT6TCx0H3x"));	
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		InputStream stream = response.getEntityInputStream();
		int total = 0;
		try {
			while( (stream.read()) != -1){
				total++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThat(total).isEqualTo(6600);
	}
	
	@Test
	public void getItem(){
		ClientResponse response = helper.get("/item/1", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void getItemByRFID(){
		ClientResponse response = helper.post("/item/rfid", helper.accessToken(), "10000000000012");
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void updateItemListNoChanges(){
		ClientResponse response = helper.get("/items", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		ItemList getList = response.getEntity(ItemList.class);
		//assertThat(getList.getItems().get(0).getCreatedDate()).isNull();
		assertThat(getList.getItems().size()).isGreaterThan(0);
		ClientResponse response2 = helper.postJSON("/items/multi", helper.accessToken(), getList);
		assertThat(response2.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
	}
	
	@Test
	public void updateItemNoChanges(){
		ClientResponse response = helper.get("/items", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		ItemList getList = response.getEntity(ItemList.class);
		//assertThat(getList.getItems().get(0).getCreatedDate()).isNull();
		assertThat(getList.getItems().size()).isGreaterThan(0);
		Item item = getList.getItems().get(0);
		ClientResponse response2 = helper.put("/item/" + item.getId(), helper.accessToken(), item);
		assertThat(response2.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
	}
	
	@Test
	public void updateItemSomeChanges(){
		ClientResponse response = helper.get("/items", helper.accessToken());
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		ItemList getList = response.getEntity(ItemList.class);
		//assertThat(getList.getItems().get(0).getCreatedDate()).isNull();
		assertThat(getList.getItems().size()).isGreaterThan(0);
		Item item = getList.getItems().get(0);
		item.setCurrentStage("ON HOLD");
		item.setRFID("AB02302948272372939102129FDE20292382323242323");
		ClientResponse response2 = helper.put("/item/" + item.getId(), helper.accessToken(), item);
		assertThat(response2.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
	}
	
	@Test
	public void createNewItem(){
		Item item = new Item();
		item.setItemId("UPN-2323-2323-2323-23");
		item.setCreatedBy("travis");
		item.setCreatedDate(new Date());
		ClientResponse response = helper.put("/item/create", helper.accessToken(), item);
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
	}
	
	@Test
	public void createSignature() throws ParseException, IllegalArgumentException, URISyntaxException, IOException {
		
		File file = new File(Resources.getResource("test.gif").toURI());
		FormDataMultiPart form = new FormDataMultiPart();
		//FormDataBodyPart body = new FormDataBodyPart("file", new FileInputStream(file), MediaType.APPLICATION_OCTET_STREAM_TYPE);
		FileDataBodyPart body = new FileDataBodyPart("file", file);
		form.field("item", "2:3:5");
		form.field("name", "trav");	
		form.bodyPart(body);
		ClientResponse response = helper.postFile("/signature/multi", helper.accessToken(), form);
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
		File newFile = new File("output.txt");
		FileWriter writer = new FileWriter(newFile);
		String result = response.getEntity(String.class);
		writer.write(result);
		writer.close();
		
		assertThat(result).isEqualTo("1");
	}
	
	@Test
	public void createNewUser(){
		User user = new User();
		user.setAdmin(false);
		user.setEmail("travis.tarr@nehp.com");
		user.setName("test5");
		user.setScanner(false);
		user.setUsername("test5");
		user.setUserCreatedDate(new Date());
		
		UserWrap wrap = new UserWrap();
		wrap.setUser(user);
		
		ClientResponse response = helper.postJSON("/users", helper.accessToken(), wrap);
		
		assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.CREATED.getStatusCode());
	}
}


