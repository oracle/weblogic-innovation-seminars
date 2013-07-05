package beans.servicemigration;

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
 * Time: 9:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceLease
{
  private String domainName;
  private String clusterName;
  private String server;
  private String instance;
  private String timeout;

  public String getDomainName()
  {
    return domainName;
  }

  public void setDomainName(String domainName)
  {
    this.domainName = domainName;
  }

  public String getClusterName()
  {
    return clusterName;
  }

  public void setClusterName(String clusterName)
  {
    this.clusterName = clusterName;
  }

  public String getServer()
  {
    return server;
  }

  public void setServer(String server)
  {
    this.server = server;
  }

  public String getInstance()
  {
    return instance;
  }

  public void setInstance(String instance)
  {
    this.instance = instance;
  }

  public String getTimeout()
  {
    return timeout;
  }

  public void setTimeout(String timeout)
  {
    this.timeout = timeout;
  }
}
