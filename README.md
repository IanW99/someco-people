### Adding your own properties to a person object in Alfresco 5.1
=================================================================
- [ ] This project is an extension to @jpotts tutorial for adding own properties to a person object in Alfresco 3.4.d.
- [ ] Built using Alfresco SDK 2.2 which is only compatible with Alfresco 5.1 while writing this tutorial.
- [ ] The major change in this project is the omission of file **userprofile.get.head.ftl** due to the reason that .head.ftl WebScript files has now been deprecated from WebScripts that render Share Components. Dependencies are now loaded through the use of the <@script> and <@link> tags in the main .html.ftl file.
- [ ] So for the above reason you may find the below code section in **userprofile.get.html.ftl** 
  ```
  <@markup id="css" >
     <#-- CSS Dependencies -->
     <@link href="${url.context}/res/components/profile/profile.css" group="profile"/>
  </@>
  
  <@markup id="js">
     <#-- JavaScript Dependencies -->
     <@script src="${url.context}/res/components/profile/profile.js" group="profile"/>
     <@script src="${url.context}/res/modules/simple-dialog.js" group="profile"/>
     <@script src="${url.context}/res/components/profile/sc-profile.js" group="profile"/>
  </@>
  ```

