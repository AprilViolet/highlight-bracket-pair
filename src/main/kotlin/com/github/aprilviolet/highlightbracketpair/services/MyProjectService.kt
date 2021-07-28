package com.github.aprilviolet.highlightbracketpair.services

import com.github.aprilviolet.highlightbracketpair.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
