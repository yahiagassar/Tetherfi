/*
 * Copyright 2024 pyamsoft
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pyamsoft.tetherfi.status

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pyamsoft.pydroid.theme.keylines
import com.pyamsoft.tetherfi.core.ExperimentalRuntimeFlags
import com.pyamsoft.tetherfi.server.ServerNetworkBand
import com.pyamsoft.tetherfi.server.status.RunningStatus
import com.pyamsoft.tetherfi.status.sections.broadcast.renderBroadcastFrequency
import com.pyamsoft.tetherfi.status.sections.network.renderNetworkInformation
import com.pyamsoft.tetherfi.ui.LANDSCAPE_MAX_WIDTH
import com.pyamsoft.tetherfi.ui.ServerViewState
import com.pyamsoft.tetherfi.ui.renderLinks
import com.pyamsoft.tetherfi.ui.test.TEST_PASSWORD
import com.pyamsoft.tetherfi.ui.test.TEST_PORT
import com.pyamsoft.tetherfi.ui.test.TEST_SSID
import com.pyamsoft.tetherfi.ui.test.TestServerState
import com.pyamsoft.tetherfi.ui.test.makeTestRuntimeFlags
import com.pyamsoft.tetherfi.ui.test.makeTestServerState
import org.jetbrains.annotations.TestOnly

private enum class StatusLoadedContentTypes {
  SPACER,
  BOTTOM_SPACER,
}

internal fun LazyListScope.renderLoadedContent(
    itemModifier: Modifier = Modifier,
    appName: String,
    state: StatusViewState,
    experimentalRuntimeFlags: ExperimentalRuntimeFlags,
    serverViewState: ServerViewState,
    isEditable: Boolean,

    // Network
    wiDiStatus: RunningStatus,
    proxyStatus: RunningStatus,
    onSsidChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onHttpPortChanged: (String) -> Unit,
    onSocksPortChanged: (String) -> Unit,
    onSelectBand: (ServerNetworkBand) -> Unit,
    // Status button
    onShowQRCode: () -> Unit,
    onRefreshConnection: () -> Unit,

    // Errors
    onShowNetworkError: () -> Unit,
    onShowHotspotError: () -> Unit,
    // Jump links
    onJumpToHowTo: () -> Unit,
    onViewSlowSpeedHelp: () -> Unit,
) {
  renderNetworkInformation(
      itemModifier = itemModifier,
      isEditable = isEditable,
      appName = appName,
      state = state,
      experimentalRuntimeFlags = experimentalRuntimeFlags,
      serverViewState = serverViewState,
      wiDiStatus = wiDiStatus,
      proxyStatus = proxyStatus,
      onSsidChanged = onSsidChanged,
      onPasswordChanged = onPasswordChanged,
      onHttpPortChanged = onHttpPortChanged,
      onSocksPortChanged = onSocksPortChanged,
      onTogglePasswordVisibility = onTogglePasswordVisibility,
      onShowQRCode = onShowQRCode,
      onRefreshConnection = onRefreshConnection,
      onShowNetworkError = onShowNetworkError,
      onShowHotspotError = onShowHotspotError,
      onJumpToHowTo = onJumpToHowTo,
      onViewSlowSpeedHelp = onViewSlowSpeedHelp,
  )

  renderBroadcastFrequency(
      itemModifier = itemModifier,
      isEditable = isEditable,
      state = state,
      serverViewState = serverViewState,
      onSelectBand = onSelectBand,
  )

  item(
      contentType = StatusLoadedContentTypes.SPACER,
  ) {
    Spacer(
        modifier = itemModifier.height(MaterialTheme.keylines.baseline),
    )
  }

  item(
      contentType = StatusLoadedContentTypes.SPACER,
  ) {
    Spacer(
        modifier = itemModifier.height(MaterialTheme.keylines.baseline),
    )
  }

  renderLinks(
      modifier = itemModifier,
      appName = appName,
  )

  item(
      contentType = StatusLoadedContentTypes.BOTTOM_SPACER,
  ) {
    Spacer(
        modifier =
            itemModifier.padding(top = MaterialTheme.keylines.content).navigationBarsPadding(),
    )
  }
}

@TestOnly
@Composable
private fun PreviewLoadedContent(
    state: TestServerState,
    isEditable: Boolean,
    socks: Boolean,
) {
  LazyColumn {
    renderLoadedContent(
        itemModifier = Modifier.widthIn(LANDSCAPE_MAX_WIDTH),
        state =
            MutableStatusViewState().apply {
              loadingState.value = StatusViewState.LoadingState.DONE
              this.ssid.value = TEST_SSID
              this.password.value = TEST_PASSWORD
              this.httpPort.value = "$TEST_PORT"
              band.value = ServerNetworkBand.LEGACY
            },
        serverViewState = makeTestServerState(state),
        experimentalRuntimeFlags = makeTestRuntimeFlags(socks),
        appName = "TEST",
        onSelectBand = {},
        onPasswordChanged = {},
        onHttpPortChanged = {},
        onSocksPortChanged = {},
        onSsidChanged = {},
        onTogglePasswordVisibility = {},
        onShowQRCode = {},
        onRefreshConnection = {},
        onShowHotspotError = {},
        onShowNetworkError = {},
        onJumpToHowTo = {},
        isEditable = isEditable,
        wiDiStatus = RunningStatus.NotRunning,
        proxyStatus = RunningStatus.NotRunning,
        onViewSlowSpeedHelp = {},
    )
  }
}

@TestOnly
@Composable
private fun PreviewEmpty(isEditable: Boolean, socks: Boolean) {
  PreviewLoadedContent(
      state = TestServerState.EMPTY,
      isEditable = isEditable,
      socks = socks,
  )
}

@TestOnly
@Composable
private fun PreviewConnected(isEditable: Boolean, socks: Boolean) {
  PreviewLoadedContent(
      state = TestServerState.CONNECTED,
      isEditable = isEditable,
      socks = socks,
  )
}

@Composable
@Preview(showBackground = true)
private fun PreviewEmptyEditableNoSocks() {
  PreviewEmpty(
      isEditable = true,
      socks = false,
  )
}

@Composable
@Preview(showBackground = true)
private fun PreviewEmptyNoEditableNoSocks() {
  PreviewEmpty(
      isEditable = false,
      socks = false,
  )
}

@Composable
@Preview(showBackground = true)
private fun PreviewConnectedEditableNoSocks() {
  PreviewConnected(
      isEditable = true,
      socks = false,
  )
}

@Composable
@Preview(showBackground = true)
private fun PreviewConnectedNoEditableNoSocks() {
  PreviewConnected(
      isEditable = false,
      socks = true,
  )
}

@Composable
@Preview(showBackground = true)
private fun PreviewEmptyEditableSocks() {
  PreviewEmpty(
      isEditable = true,
      socks = true,
  )
}

@Composable
@Preview(showBackground = true)
private fun PreviewEmptyNoEditableSocks() {
  PreviewEmpty(
      isEditable = false,
      socks = true,
  )
}

@Composable
@Preview(showBackground = true)
private fun PreviewConnectedEditableSocks() {
  PreviewConnected(
      isEditable = true,
      socks = true,
  )
}

@Composable
@Preview(showBackground = true)
private fun PreviewConnectedNoEditableSocks() {
  PreviewConnected(
      isEditable = false,
      socks = true,
  )
}
