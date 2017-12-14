scrpt=$(<$1)
scrpt2= {$scrpt | sed 's/\\n//g'}
echo $scrpt2 > ui.log