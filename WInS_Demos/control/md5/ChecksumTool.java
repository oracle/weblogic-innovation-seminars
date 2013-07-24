import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class ChecksumTool
{
  public static final int ONE_MB = 1048576;
  public static final int BUFFER = ONE_MB * 2;

  public static void main(String[] args)
  {
    if (args.length == 0)
    {
      System.out.println("This program requires at least one arg");
      System.exit(1);
    }

    String dirname = args[0];
    File dir = new File(dirname);

    File[] files = dir.listFiles();

    for (File file : files)
    {
      try
      {
        String md5 = getMD5Checksum(file, BUFFER);
        FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath() + ".md5");
        String fileContents = "MD5 (" + file.getAbsolutePath() + ") = " + md5;
        fileOut.write(fileContents.getBytes());
        fileOut.close();
        System.out.println(fileContents);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }

  }

  public static String getMD5Checksum(File file, int pBufferSize) throws IOException
  {
    StringBuffer checksum = null;
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      FileInputStream is = new FileInputStream(file);

      byte buffer[] = new byte[pBufferSize];

      int bytesRead = 0;

      while ((bytesRead = is.read(buffer)) > 0)
      {
        md.update(buffer, 0, bytesRead);
      }

      byte digest[] = md.digest();
      checksum = new StringBuffer(79);

      for (byte aDigest : digest)
      {
        String digit = Integer.toHexString(0xff & aDigest);

        if (digit.length() == 1)
        {
          checksum.append('0');
        }

        checksum.append(digit);
      }
      is.close();
    }
    catch (NoSuchAlgorithmException e)
    {

    }
    return checksum.toString();
  }

  public static String getCRC32Checksum(File file, int pByteArraySize) throws IOException
  {
    final InputStream sourceInputStream;
    final FileInputStream fileIn = new FileInputStream(file);

    sourceInputStream = new BufferedInputStream(fileIn, pByteArraySize);

    return getCRC32Checksum(sourceInputStream, pByteArraySize);
  }

  private static String getCRC32Checksum(InputStream sourceInputStream, int pByteArraySize) throws IOException
  {
    long decimalChecksum = 0;
    String hexChecksum = null;

    final String ZEROS = "00000000";
    final int CRC32_CHECKSUM_LENGTH = 8;
    CRC32 checksumEngine = new CRC32();
    CheckedInputStream cis = new CheckedInputStream(sourceInputStream, checksumEngine);

    // read the io
    byte[] tempBuf = new byte[pByteArraySize];
    while (cis.read(tempBuf) >= 0)
    {
    }

    // return scanner
    decimalChecksum = cis.getChecksum().getValue();
    hexChecksum = Long.toHexString(decimalChecksum).toUpperCase();

    // pad leading "0" if needed
//		if ( hexChecksum.length() % 2 != 0) {
//			hexChecksum = "0" + hexChecksum;
//		}
    hexChecksum = ZEROS.substring(0, CRC32_CHECKSUM_LENGTH - hexChecksum.length()) + hexChecksum;
    // release io handle
    cis.close();
    sourceInputStream.close();

    return hexChecksum;
  }

  public static String getCRC32Checksumd(byte[] bytes)
  {
    ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

    try
    {
      return getCRC32Checksum(stream, bytes.length);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        stream.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }

    return null;
  }

  public static long checksumMappedFile(final File pFile) throws IOException
  {
    FileInputStream in = new FileInputStream(pFile);

    try
    {
      FileChannel channel = in.getChannel();

      CRC32 crc = new CRC32();
      int length = (int) channel.size();

      MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);

      for (int p = 0; p < length; p++)
      {
        int c = buffer.get(p);
        crc.update(c);
//      buffer.
      }
      return crc.getValue();
    }
    finally
    {
      in.close();
    }
  }

  public static long checksumMappedFile(String filename) throws IOException
  {
    return checksumMappedFile(new File(filename));
  }

  public static long getCRC32Checksum(byte[] bytes) throws IOException
  {
    ByteBuffer buffer = ByteBuffer.wrap(bytes);

    CRC32 crc = new CRC32();
    crc.update(bytes);
    return crc.getValue();
  }

}