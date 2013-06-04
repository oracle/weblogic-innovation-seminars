package com.oracle.demo.ops.string;

import com.oracle.demo.ops.domain.ParcelEvent;

/**
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
 * * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 4/30/12
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils
{
  public static String toString(ParcelEvent event)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Date: ").append(event.getDate());
    sb.append(" | Parcel ID: ").append(event.getParcelId());
    sb.append(" | Location: ").append(event.getLocation());
    sb.append(" | Status: ").append(event.getParcelStatus().value());
    sb.append(" | Message: ").append(event.getMessage());

    return sb.toString();
  }
}
