cd dist/
git checkout .gitignore
git add --all javadoc/.
git add --all GameTools.jar
git commit -m "Updated javadoc and jar"
git push origin gh-pages
pause