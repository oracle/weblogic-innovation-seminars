package beans.servicemigration;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
 * Date: 7/5/13
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@SessionScoped
public class ServiceMigrationController
{
  @Resource(name = "jdbc.ds.weblogic_examples")
  private javax.sql.DataSource ds;

  public List<ServiceLease> getServiceLeases()
  {
    List<ServiceLease> activeTableRows = new ArrayList<ServiceLease>(5);

    Connection conn = null;
    try
    {
      conn = ds.getConnection();

      ResultSet rs = conn.createStatement().executeQuery("select * from active");

      while(rs.next())
      {
        ServiceLease row = new ServiceLease();
        row.setClusterName(rs.getString("CLUSTERNAME"));
        row.setInstance(rs.getString("INSTANCE"));
        row.setServer(rs.getString("SERVER"));
        row.setDomainName(rs.getString("DOMAINNAME"));
        row.setTimeout(rs.getString("TIMEOUT"));
        activeTableRows.add(row);
      }

    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (conn != null)
      {
        try
        {
          conn.close();
        }
        catch (SQLException ignore)
        {

        }
      }
    }

    return activeTableRows;
  }

  public String openLeaseTableMonitor()
  {
    return null;
  }
}
