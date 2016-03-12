if (typeof SomeCo == "undefined" || !SomeCo)
{
   var SomeCo = {};
}

/**
 * Customized User Profile component.
 * 
 * @namespace SomeCo
 * @class SomeCo.UserProfile
 * @extends Alfresco.UserProfile
 */
(function()
{
   /**
    * YUI Library aliases
    */
   var Dom = YAHOO.util.Dom,
      Event = YAHOO.util.Event,
      Element = YAHOO.util.Element;
   
   /**
    * UserProfile constructor.
    * 
    * @param {String} htmlId The HTML id of the parent element
    * @return {Alfresco.UserProfile} The new UserProfile instance
    * @constructor
    */
   SomeCo.UserProfile = function(htmlId)
   {
      SomeCo.UserProfile.superclass.constructor.call(this, htmlId);
      
      // Re-register with our own name
      this.name = "SomeCo.UserProfile";
      Alfresco.util.ComponentManager.reregister(this);
      
      return this;
   };
   
   YAHOO.extend(SomeCo.UserProfile, Alfresco.UserProfile,
   {
      
      /**
       * Edit Profile button click handler
       * 
       * @method onEditProfile
       * @param e {object} DomEvent
       * @param p_obj {object} Object passed back from addListener method
       */
      onEditProfile: function UP_onEditProfile(e, p_obj)
      {
         // Hide view panel
         Dom.addClass(this.id + "-readview", "hidden");

         // Reset form data
         var p = this.options.profile,
            prefix = this.id + "-input-";
         Dom.get(prefix + "lastName").value = p.lastName;
         Dom.get(prefix + "firstName").value = p.firstName;
         Dom.get(prefix + "jobtitle").value = p.jobtitle;
         Dom.get(prefix + "location").value = p.location;
         Dom.get(prefix + "bio").value = p.bio;
         Dom.get(prefix + "telephone").value = p.telephone;
         Dom.get(prefix + "mobile").value = p.mobile;
         Dom.get(prefix + "email").value = p.email;
         Dom.get(prefix + "skype").value = p.skype;
         Dom.get(prefix + "instantmsg").value = p.instantmsg;
         Dom.get(prefix + "googleusername").value = p.googleusername;
         Dom.get(prefix + "organization").value = p.organization;
         Dom.get(prefix + "companyaddress1").value = p.companyaddress1;
         Dom.get(prefix + "companyaddress2").value = p.companyaddress2;
         Dom.get(prefix + "companyaddress3").value = p.companyaddress3;
         Dom.get(prefix + "companypostcode").value = p.companypostcode;
         Dom.get(prefix + "companytelephone").value = p.companytelephone;
         Dom.get(prefix + "companyfax").value = p.companyfax;
         Dom.get(prefix + "companyemail").value = p.companyemail;
         // SomeCo Specific properties
         Dom.get(prefix + "birthDate").value = p.birthDate;
         Dom.get(prefix + "shirtSize").value = p.shirtSize;
         Dom.get(prefix + "significantOther").value = p.significantOther;
         Dom.get(prefix + "likesNeilDiamond").value = p.likesNeilDiamond;
         
         this.widgets.form.updateSubmitElements();
         
         // Show edit panel
         Dom.removeClass(this.id + "-editview", "hidden");
      },

   });
})();