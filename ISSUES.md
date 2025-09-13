# M1

## List of issues

### Issue 1: "Delete Account" Not Working

**Description**: The "Delete Account" button on the app logs the user out rather than deleting their account, therefore the account still exists.

**How it was fixed?**: I fixed this issue by adding a delete account API to the UserInterface in ProfileInterface.kt and adding a deleteAccount function, implemented in ProfileRepositoryImpl.kt. The function calls on the API in UserInterface and either has a success or a fail depending on the response. I added a delete account function in ProfileViewModel.kt to make the delete feature accessible there, and lastly edited the confirmation handler to only handle the deletion of the account when it was successful. 

### Issue 2: Bio Cannot Be Edited

**Description**: The user Bio on the profile page cannot be edited, and remains as the fixed value initially entered on profile creation.

**How it was fixed?**: This issue had the simplest fix; in ManageProfileScreen.kt, I removed the line "readOnly = true", which set the bio to be uneditable. Additionally, I removed the wrapper that was formerly around all the OutlinedTextFields that prevented them from being altered.

### Issue 3: Profile Picture Not Saving

**Description**: The profile picture in the user's profile only saves temporarily, and whenever a save button is pressed anywhere within the app, the picture is erased.

**How it was fixed?**: I added a function for uploading the profile picture to the backend in ProfileRepository.kt and then implemented it in its respective Impl file. This allowed the frontend to make an API request to the backend and upload the image, or catch a failure if this didn't work. I then edited the uploadProfilePicture() section in ProfileViewModel.kt to reflect the changes by providing a success message when the photo had been uploaded and displaying the photo on the profile page.

### Issue 4: Hobbies Save Button Not Necessary

**Description**: The save button on the Hobbies page is useless as the hobbies are saved by default.

**How it was fixed?**: This issue also had a fairly simple fix; I had to remove all instances of the save button, from its functionality to its UI display. I first removed all "onSaveClick" lines from related Hobbies functions and classes in ManageHobbiesScreen.kt, and then fully deleted the SaveButton() block inside of the HobbiesForm. After making sure that everything related to the hobbies button had been removed, the fix was complete.

### Issue 5: Disorganized Backend Folder Structure 

**Description**: The file structure within the app's backend is incredibly disorganized and there are many file that can be grouped into subdirectories of backend/src.

**How it was fixed?**: A fairly quick fix, this required going into the backend structure and looking through the file names for common denominators. Files named "something.controller.ts" were grouped into a controllers directory, files named "something.routes.ts" into a routes directory, and so forth. I then accessed every single one of these files to update path references, as most of them referenced other files under backend/src that had now been moved. 