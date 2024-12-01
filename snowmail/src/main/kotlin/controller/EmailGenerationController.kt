package controller

import ca.uwaterloo.model.EducationWithDegreeName
import ca.uwaterloo.model.PersonalProject
import ca.uwaterloo.model.WorkExperience
import model.GeneratedEmail
import service.EmailGenerationService
import model.UserInput
import model.UserProfile
import java.io.File


class EmailGenerationController(private val emailGenerationService: EmailGenerationService) {

    suspend fun generateEmail(
        informationSource: String,
        userInput: UserInput,
        userProfile: UserProfile,
        education: List<EducationWithDegreeName> = emptyList(),
        workExperience: List<WorkExperience> = emptyList(),
        projects: List<PersonalProject> = emptyList(),
        skills: List<String> = emptyList(),
        resumeFile: File? = null
    ): GeneratedEmail? {
        println("resumeFile: ${resumeFile?.path ?: "No file provided"}") // Console log to check if resumeFile is being passed

        return if (resumeFile != null && informationSource == "resume") {
            // console log to check if this executes
            println("Generating email from resume; file is: " + resumeFile.exists())
            val rFile: File = resumeFile as File
            println("rFile: " + rFile.isFile)
            emailGenerationService.generateEmailFromResume(userInput, userProfile, rFile)
        } else {
            emailGenerationService.generateEmailFromProfile(userInput, userProfile, education, workExperience, projects, skills)
        }
    }

}

