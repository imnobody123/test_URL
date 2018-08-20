package test_URL;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.omg.CORBA.portable.InputStream;

public class ClasePrincipal
{
	public static void main(String[] args) throws IOException
	{
		CrearDirectorio();
		try
		{
			URL url = new URL("http://cbtis65.identaplus.net/servidor/fotos/");
			DataInputStream leer = new DataInputStream(url.openStream());
			String URL = String.valueOf(url);
			String LineaEntrada;
			
			while((LineaEntrada = leer.readLine()) != null)
			{
				if(LineaEntrada.contains("a href=") && LineaEntrada.contains(".jpg"))
				{
					String[] partes = LineaEntrada.split("\"");
					String CURP = partes[7];
					System.out.println(CURP);
					
					File dir = new File("/home/joshua/DescargasImagenes/" + CURP);
					URLConnection conectar = new URL(URL + CURP).openConnection();
					conectar.connect();
					System.out.println("Empezando descarga...");
					System.out.println(">> URL: " + URL + CURP);
					System.out.println(">> Nombre: " + CURP);
					System.out.println(">> Tamaño: " + conectar.getContentLength() + "bytes");
					
					java.io.InputStream input = conectar.getInputStream();
					OutputStream output = new FileOutputStream(dir);
					
					int b = 0;
					while (b != -1)
					{
						b = input.read();
						if (b != -1)
						{
							output.write(b);
						}
					}
					
					output.close();
					input.close();
				}
			}
			leer.close();
		}
		catch(MalformedURLException me)
		{
			System.out.println("MalformedURLException: " + me);
		}
		catch(IOException ioe)
		{
			System.out.println("IOException: " + ioe);
		}
	}
	
	public static void CrearDirectorio()
	{
		String folder = "/home/joshua/DescargasImagenes/";
		File carpeta = new File(folder);
		if (!carpeta.exists())
		{
			if (!carpeta.mkdirs())
			{
				System.out.println("La carpeta no se ha podido crear");
			}
			else
			{
				System.out.println("La carpeta fue creada exitosamente");
			}
		}
		else
		{
			System.out.println("La carpeta ya existe");
		}
	}
}
