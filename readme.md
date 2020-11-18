# Pdf converter library
## Version :
Beta
## Dependencies :
Java 8 or greater 

```xml
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.13.1</version>
</dependency>
```

## Inputs :
- Folder
- Zip file
- Pdf file
- PNG file
- JPEG file
- JPG file
- TIFF file
- TXT file
- PPT(X file)
- DOC(X file)
- XLS(X file)

## Output :
List of all processed files

## Pipline :
1. Files are pipelined throw processors (Virus scan, ...)
2. Next, they are pipelined through converters
3. Next, the resulting pdf files are pipelined through transformers (Scale, Change paper format, ...)
4. Last, they are stored on the output directory

## Usage :
```java
static void run(String path, double scale, Format format) {
		try {

			PdfConverterEngine engine = PdfConverterEngine.engine();
			// Ex : engine.addFileConverter(converter);
			Configuration conf = Configuration.from(new File("./tmp"), new File("./out"), new ArrayList<>());
			// Ex : conf.addFileProcessor(processor);
			FileData fileData = FileData.from(new File(path), scale, format);
			
			List<FileResponse> files = engine.convert(fileData, conf);

			System.out.println("End");
			files.stream().forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

```

## roadmap
- Implements the DOCXFileConverter
- Implements the PPTXFileConverter
- Implements the XLSXFileConverter

## Motivation :
Inspired from a job post on Upwork