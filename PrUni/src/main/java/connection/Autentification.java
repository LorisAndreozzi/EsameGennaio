package connection;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Autentification {

	private String bearer = "";
	
	public Autentification(String nomeFile) 
	{
		String tokenCompleto = "";
		
		try 
		{
			InputStream in = new FileInputStream(nomeFile);
			
			try 
			{
				InputStreamReader letturaFile = new InputStreamReader(in);
				BufferedReader letturaBufferizzata = new BufferedReader( letturaFile );
			
				String riga = "";
			
				while ( ( riga = letturaBufferizzata.readLine() ) != null ) 
				{
					tokenCompleto += riga;
				}
				
			this.bearer = tokenCompleto;
			
			}finally
			{
				in.close();
			}
						
		}catch(Exception errore) 
		{
			System.out.println(errore);
			System.out.println("Errore di autentificazione.");
		}
		
	}

	public String getBearer() {
		return bearer;
	}	
	
}
