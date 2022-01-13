package tests;

import controllers.ControllerRest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ControllersTest {

	private ControllerRest cRTest1;
	
	@BeforeEach
	void setUp() throws Exception
	{
		String jsonProvvisorio = "";
		
		try 
		{
			InputStream in = new FileInputStream("JsonForTests.json");
			
			try 
			{
				InputStreamReader letturaFile = new InputStreamReader(in);
				BufferedReader letturaBufferizzata = new BufferedReader( letturaFile );
			
				String riga = "";
			
				while ( ( riga = letturaBufferizzata.readLine() ) != null ) 
				{
					jsonProvvisorio += riga;
				}
				
			
			}finally
			{
				in.close();
			}
						
		}catch(IOException errore) 
		{
			System.out.println(errore);
			System.out.println("Errore IO....");
		}
		cRTest1 = new ControllerRest(jsonProvvisorio);
	}
	
	@AfterEach
	void tearDown() throws Exception
	{}
	
	@Test
	void Test() 
	{
		
		// test effettuati  sul metodo "filtroSerachPer"
		assertEquals((""+(((JSONObject)((cRTest1.filtroSerachPer("moreThan",(long)100,"like")).get("statistics"))).get("filtrPercent"))),"40.0");
		assertEquals((""+(((JSONObject)((cRTest1.filtroSerachPer("lessThan",(long)100,"like")).get("statistics"))).get("filtrPercent"))),"60.0");
		assertEquals((""+(cRTest1.filtroSerachPer("lessThan",(long)-100,"like")).get("Eccezione")),"400 BAD_REQUEST \"Use the only known parameters\"");
		assertEquals((""+(((JSONObject)((cRTest1.filtroSerachPer("moreThan",(long)1000,"like")).get("statistics"))).get("filtrPercent"))),"0");
		assertEquals((""+(((JSONObject)((cRTest1.filtroSerachPer("moreThan",(long)5,"like")).get("statistics"))).get("filtrPercent"))),"100");
		
		// test effettuati  sul metodo "trovaMinMaxFromSerach"
		assertEquals((""+(((JSONObject)(cRTest1.trovaMinMaxFromSerach("Max","like"))).get("tweetId"))),"1480172939353985028");
		assertEquals((""+(((JSONObject)(cRTest1.trovaMinMaxFromSerach("min","like"))).get("tweetId"))),"1480230886213828616");
		assertEquals((""+(((JSONObject)(cRTest1.trovaMinMaxFromSerach("Msadaasdax","like"))).get("Eccezione"))),"400 BAD_REQUEST \"Use the only known parameters\"");
	}
	
}
