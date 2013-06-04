package com.oracle.weblogic.examples.spring.jmx;

import javax.management.Descriptor;

import org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;
import org.springframework.jmx.export.assembler.SimpleReflectiveMBeanInfoAssembler;

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
 * User: jeffrey.a.west
 * Date: 4/3/12
 * Time: 2:39 PM
 */
public class WLDFAwareReflectiveMBeanInfoAssembler
    extends SimpleReflectiveMBeanInfoAssembler
{
  private static final String WLDF_MBEAN_TYPE_DESCPTR_KEY = "DiagnosticTypeName";
  private static final String NAME_MBEAN_DESCPTR_KEY = "name";
  private static final String MBEAN_KEYNAME_SUFFIX = "MBean";

  @Override
  protected void populateMBeanDescriptor(Descriptor descriptor, Object managedBean, String beanKey)
  {
    super.populateMBeanDescriptor(descriptor, managedBean, beanKey);
    descriptor.setField(WLDF_MBEAN_TYPE_DESCPTR_KEY, descriptor.getFieldValue(NAME_MBEAN_DESCPTR_KEY) + MBEAN_KEYNAME_SUFFIX);
  }
}