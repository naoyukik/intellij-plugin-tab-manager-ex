package com.github.naoyukik.ijtabmanagerex.userInterface

import com.github.naoyukik.ijtabmanagerex.application.TabPinningService
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service

class UnpinAllAction : AnAction() {
    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val pinningService = project.service<TabPinningService>()
        pinningService.unpinAll()
    }
}
