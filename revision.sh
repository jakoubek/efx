file=conf/messages.xx

version=$1
releasedate=$(date +%Y-%m-%d)
rev=`git log --abbrev-commit -1 --pretty=%h`

echo "version=$version" > $file
echo "releasedate=$releasedate" >> $file
echo "revision=$rev" >> $file
