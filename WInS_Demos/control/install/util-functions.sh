#!/bin/sh

#----------------------

exec_command()
{
	#Show the command
	echo "Executing command: [$2]"

		# Execute the command
		echo "Started at `date`"
		start_date=`date +%s`

		${2}

		if [ $? -ne 0 ]; then
			end_date=`date +%s`
			duration=$(echo "scale=2; ($end_date-$start_date)/60" | bc)
      echo "### !!!!! ###"
      echo "--Command complete (error) in ${duration} minutes"
      echo "Error installing SW Package: $1"
      echo "### !!!!! ###"
      exit 1
		else
			end_date=`date +%s`
			duration=$(echo "scale=2; ($end_date-$start_date)/60" | bc)
			echo "++Command complete (success) in ${duration} minutes"
		fi
}

#----------------------

check_network()
{
  THE_HOSTNAME=`hostname`

  if [ "$THE_HOSTNAME" != "wins-vbox.localdomain" ];
  then
    echo "### !!!!! ###"
    echo "Hostname (wins-vbox.localdomain) didn't take effect.  The hostname of the machine must be set to wins-vbox.localdomain before proceeding!"
    echo "### !!!!! ###"
    exit 1
  else
    echo "Hostname (wins-vbox.localdomain) successfully applied!"
  fi

  RETVAL=$?

  if [ "$RETVAL" != "0" ];
  then
    echo "### !!!!! ###"
    echo "IP Address (172.16.254.254) didn't take effect.  This IP address must be applied to the internally-networked virtualbox network adapter before proceeding"
    echo "### !!!!! ###"
    exit 1
  else
    echo "IP Address (172.16.254.254) successfully applied!"
  fi
}