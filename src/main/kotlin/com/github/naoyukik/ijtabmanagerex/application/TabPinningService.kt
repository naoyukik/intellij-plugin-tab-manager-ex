package com.github.naoyukik.ijtabmanagerex.application

import com.intellij.openapi.components.Service
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.Project
import com.intellij.util.concurrency.annotations.RequiresEdt

@Service(Service.Level.PROJECT)
class TabPinningService(val project: Project) {
    @RequiresEdt
    fun pinAllOpenFiles() {
        val editorManager = FileEditorManagerEx.getInstanceEx(project)
        editorManager.windows.forEach { window ->
            window.fileList.forEach { file ->
                window.setFilePinned(file, true)
            }
        }
    }

    @RequiresEdt
    fun unpinAll() {
        val editorManager = FileEditorManagerEx.getInstanceEx(project)
        editorManager.windows.forEach { window ->
            window.fileList.forEach { file ->
                window.setFilePinned(file, false)
            }
        }
    }
}
