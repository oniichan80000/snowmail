package ca.uwaterloo.persistence

import ca.uwaterloo.model.Education
import ca.uwaterloo.model.WorkExperience
import ca.uwaterloo.model.UserProfile
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.datetime.LocalDate

class UserProfileRepository(private val supabase: SupabaseClient) {

    suspend fun getUserName(userId: String): Result<String> {
        return try {
            // fetch user's name from db based on userid
            val userProfile = supabase.from("user_profile")
                .select(columns = Columns.list("user_id", "first_name", "last_name")) {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeSingle<UserProfile>()

            Result.success("${userProfile.first_name} ${userProfile.last_name}")
        } catch (e: Exception) {
            Result.failure(Exception("Failed to fetch profile: ${e.message}"))
        }
    }

    //
    //education experience
    //
    suspend fun getEducation(userId: String): Result<List<Education>> {
        return try {
            val educationList = supabase.from("education")
                .select(columns = Columns.list("id", "user_id", "degree_id", "institution_name", "major", "gpa", "start_date", "end_date")) {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<Education>()

            Result.success(educationList)
        } catch (e: Exception) {
            Result.failure(Exception("Failed to fetch education records: ${e.message}"))
        }
    }

    suspend fun addEducation(
        userId: String,
        degreeId: Int,
        major: String,
        gpa: Float?,
        startDate: LocalDate,
        endDate: LocalDate,
        institutionName: String
    ): Result<Boolean> {
        return try {
            val education = Education(
                userId = userId,
                degreeId = degreeId,
                institutionName = institutionName,
                major = major,
                gpa = gpa,
                startDate = startDate,
                endDate = endDate
            )
            supabase.from("education").insert(education)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(Exception("Failed to add education: ${e.message}"))
        }
    }


    //
    //working experience
    //
    suspend fun getWorkExperience(userId: String): Result<List<WorkExperience>> {
        return try {
            val workExperienceList = supabase.from("work_experience")
                .select(columns = Columns.list("id", "user_id", "company_name", "currently_working", "title", "start_date", "end_date", "description")) {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<WorkExperience>()

            Result.success(workExperienceList)
        } catch (e: Exception) {
            Result.failure(Exception("Failed to fetch work experience records: ${e.message}"))
        }
    }

    suspend fun addWorkExperience(
        userId: String,
        companyName: String,
        currentlyWorking: Boolean,
        title: String,
        startDate: LocalDate,
        endDate: LocalDate,
        description: String?
    ) : Result<Boolean>{
        return try {
            val workExperience = WorkExperience(
                userId = userId,
                companyName = companyName,
                currentlyWorking = currentlyWorking,
                title = title,
                startDate = startDate,
                endDate = endDate,
                description = description
            )
            supabase.from("work_experience").insert(workExperience)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(Exception("Failed to add working experience: ${e.message}"))
        }
    }



}

