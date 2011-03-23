#file=conf/messages.xx
file=app/views/tags/version.html

version=$1
releasedate=$(date +%Y-%m-%d)
#rev=`git log --abbrev-commit -1 --pretty=%h`

#echo "version=$version" > $file
#echo "releasedate=$releasedate" >> $file
#echo "revision=$rev" >> $file

echo "<span title=\"&{'app.version'}\">Version $version</span> (<span title=\"&{'app.releasedate'}\">$releasedate</span>)" > $file