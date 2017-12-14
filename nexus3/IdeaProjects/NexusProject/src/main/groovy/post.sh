scrpt=$(<$1)
scrpt=$(echo $scrpt | sed 's/^package.*?\n//g')
scrpt=$(echo $scrpt | sed "s/package.*?;//g")
scrpt=$(echo $scrpt | sed 's/\n//g')
scrpt=$(echo $scrpt | sed 's/\r//g')
echo $scrpt > ui.log