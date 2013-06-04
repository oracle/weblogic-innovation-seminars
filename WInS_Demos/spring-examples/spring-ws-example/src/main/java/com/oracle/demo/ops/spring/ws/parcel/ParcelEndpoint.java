package com.oracle.demo.ops.spring.ws.parcel;

import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


/**
  * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 *
 * This code is provided under the following licenses:
 *
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 *
 * <p/>
 * ****************************************************************************
 * User: jeffrey.a.west
 * Date: Feb 16, 2011
 * Time: 4:57:26 PM
 */
@Endpoint
public class ParcelEndpoint
    implements ParcelService
{
  private ParcelService parcelService;

  @Autowired
  public void setParcelService(ParcelService parcelService)
  {
    this.parcelService = parcelService;
  }

  @PayloadRoot(localPart = "AddParcelLogEventJMSPROXYRequest", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
  @ResponsePayload
  public AddParcelLogEventJMSPROXYResponse addParcelEventJMSPROXY(@RequestPayload AddParcelLogEventJMSPROXYRequest pRequest)
  {
//    System.out.println("addParcelEventJMSPROXY");
    return parcelService.addParcelEventJMSPROXY(pRequest);

  }

  @PayloadRoot(localPart = "GetParcelEventLogRequest", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
  @ResponsePayload
  public GetParcelEventLogResponse getParcelEvents(@RequestPayload GetParcelEventLogRequest pRequest)
  {
    //System.out.println("getParcelEvents");
    return parcelService.getParcelEvents(pRequest);
  }

  @PayloadRoot(localPart = "GetParcelByIdRequest", namespace = "http://demo.oracle.com/ops/domain/services/parcel")
  @ResponsePayload
  public GetParcelByIdResponse getParcelById(@RequestPayload GetParcelByIdRequest pRequest)
  {
    //System.out.println("getParcelById");
    return parcelService.getParcelById(pRequest);
  }

  @Override
  public void publishParcelEvent(ParcelEvent event)
  {

  }
}
