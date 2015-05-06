import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * Created by evrignaud on 05/05/15.
 */
public class StateGenerator
{
	public State generateState(String message, File baseDirectory) throws IOException
	{
		State state = new State();
		state.message = message;
		state.baseDirectory = baseDirectory.toString();
		getFileStates(state, baseDirectory);
		return state;
	}

	private void getFileStates(State state, File directory)
	{
		String baseDirectory = state.baseDirectory;
		File[] files = directory.listFiles();
		for (File file : files)
		{
			if (file.isDirectory() && file.getName().equals(".bm"))
			{
				continue;
			}
			
			if (file.isDirectory())
			{
				getFileStates(state, file);
			}
			else
			{
				String hash = hashFile(file);
				String fileName = file.toString();
				fileName = getRelativeFileName(baseDirectory, fileName);
				state.fileStates.add(new FileState(fileName, file.lastModified(), hash));
			}
		}
	}

	private String getRelativeFileName(String baseDirectory, String fileName)
	{
		if (fileName.startsWith(baseDirectory))
		{
			fileName = fileName.substring(baseDirectory.length());
		}
		if (fileName.startsWith("/"))
		{
			fileName = fileName.substring(1);
		}
		return fileName;
	}

	private String hashFile(File file)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			FileInputStream fis = new FileInputStream(file);

			byte[] dataBytes = new byte[1024];

			int nread;
			while ((nread = fis.read(dataBytes)) != -1)
			{
				md.update(dataBytes, 0, nread);
			}

			byte[] mdbytes = md.digest();

			StringBuffer hexString = new StringBuffer();
			for (byte b : mdbytes)
			{
				hexString.append(String.format("%x", b));
			}

			return hexString.toString();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "????";
		}
	}
}
