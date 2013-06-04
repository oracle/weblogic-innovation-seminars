package com.oracle.demo.ops.spring.ws.shipment;

import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.services.ShipmentService;
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
 * Time: 4:57:18 PM
 */
@Endpoint
public class ShipmentEndpoint
{
  private ShipmentService shipmentService;

  @Autowired
  public ShipmentEndpoint(ShipmentService shipmentService)
  {
    this.shipmentService = shipmentService;
  }

  @PayloadRoot(localPart = "CreateShipmentRequest", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
  @ResponsePayload
  public CreateShipmentResponse createShipment(@RequestPayload CreateShipmentRequest pRequest)
  {
    //System.out.println("ShipmentEndpoint.createShipment");

    return shipmentService.createShipment(pRequest);
  }

  @PayloadRoot(localPart = "SendNewShipmentViaJMSRequest", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
  @ResponsePayload
  public SendNewShipmentViaJMSResponse sendNewShipmentViaJMS(@RequestPayload SendNewShipmentViaJMSRequest pRequest)
  {
    //System.out.println("ShipmentEndpoint.sendNewShipmentViaJMS");

    return shipmentService.sendNewShipmentViaJMS(pRequest);
  }

  @PayloadRoot(localPart = "GetShipmentByIdRequest", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
  @ResponsePayload
  public GetShipmentByIdResponse getShipmentById(@RequestPayload GetShipmentByIdRequest pRequest)
  {
    //System.out.println("ShipmentEndpoint.getShipmentById");

    return shipmentService.getShipmentById(pRequest);
  }

  @PayloadRoot(localPart = "GetShipmentByExternalIdRequest", namespace = "http://demo.oracle.com/ops/domain/services/shipment")
  @ResponsePayload
  public GetShipmentByExternalIdResponse getShipmentByExternalId(@RequestPayload GetShipmentByExternalIdRequest pRequest)
  {
    //System.out.println("ShipmentEndpoint.getShipmentByExternalId");

    return shipmentService.getShipmentByExternalId(pRequest);
  }
}
