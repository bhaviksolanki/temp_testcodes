
public interface IZipService {
	String generate Zip (String outFilepath, String zipFilename, List<String> fileNames) throws IOException;
}