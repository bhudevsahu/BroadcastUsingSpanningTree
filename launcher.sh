cat conf.txt | sed -e "s/#.*//" | sed -e "/^\s*$/d" |
(
    read i
    echo $i
    while read -r line || [[ -n $line ]];
    do
        host=$( echo $line | awk '{ print $1 }' )
        ssh bxs123330@$host "cd $HOME/AOS; java BroadcastMain" &
    done
   
)

