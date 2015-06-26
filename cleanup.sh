cat conf.txt | sed -e "s/#.*//" | sed -e "/^\s*$/d" |
(
    read i
    echo $i
    while read -r line || [[ -n $line ]];
    do
        host=$( echo $line | awk '{ print $1 }' )

        echo $host
        ssh bxs123330@$host killall -u bxs123330 &
        sleep 1
    done
   
)


echo "Cleanup complete"
