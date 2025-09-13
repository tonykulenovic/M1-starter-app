# M1

## New Feature

**Name:** Book Recommendation Tool

**Short description:** My new feature implements an API call to Open Library's Book Search tool based on the user's hobby selections, and returns a scrollable list of related books to the main page of the user's UI. The user can toggle their hobby preferences to generate a new list of recommended books. 

**Location and code:** 
- `frontend/app/src/main/java/com/cpen321/usermanagement/data/remote/dto/BookModels.kt`
- `frontend/app/src/main/java/com/cpen321/usermanagement/data/remote/api/BookInterface.kt`
- `frontend/app/src/main/java/com/cpen321/usermanagement/data/remote/api/RetrofitClient.kt`, lines 10, 38-42, 48
- `frontend/app/src/main/java/com/cpen321/usermanagement/di/NetworkModule.kt`, lines 13, 43-47
- `frontend/app/src/main/java/com/cpen321/usermanagement/data/repository/BookRepository.kt`
- `frontend/app/src/main/java/com/cpen321/usermanagement/data/repository/BookRepositoryImpl.kt`
- `frontend/app/src/main/java/com/cpen321/usermanagement/di/RepositoryModule.kt`, lines 12-13, 35-41
- `frontend/app/src/main/java/com/cpen321/usermanagement/ui/viewmodels/MainViewModel.kt`, lines 4-7, 12, 17-20, 25, 39-72
- `frontend/app/src/main/java/com/cpen321/usermanagement/ui/screens/MainScreen.kt`, lines 3, 5, 7, 9-10, 13-20, 30, 36, 39-42, 44, 49, 51, 63-67, 188-219, 228-378
- `frontend/app/src/main/java/com/cpen321/usermanagement/ui/navigation/Navigation.kt`, line 181
