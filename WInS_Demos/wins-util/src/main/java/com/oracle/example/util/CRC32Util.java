package com.oracle.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * /*
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 * <p/>
 * This code is provided under the following licenses:
 * <p/>
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 * <p/>
 * <p/>
 * ****************************************************************************
 * Created with IntelliJ IDEA because its awesome.
 * User: jeffreyawest
 * Date: 5/7/13
 * Time: 2:01 PM
 */
public abstract class CRC32Util
{
  public static String getCRC32Checksum(String pString)
  {
    long decimalChecksum = 0;
    String hexChecksum = null;

    final String ZEROS = "00000000";
    final int CRC32_CHECKSUM_LENGTH = 8;
    CRC32 checksumEngine = new CRC32();

    checksumEngine.update(pString.getBytes());
    decimalChecksum = checksumEngine.getValue();
    hexChecksum = Long.toHexString(decimalChecksum).toUpperCase();
    hexChecksum = ZEROS.substring(0, CRC32_CHECKSUM_LENGTH - hexChecksum.length()) + hexChecksum;

    return hexChecksum;
  }
}
