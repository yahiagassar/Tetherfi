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

package com.pyamsoft.tetherfi.status.trouble

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.pyamsoft.pydroid.theme.keylines
import com.pyamsoft.tetherfi.status.R

@Composable
internal fun TroubleshootUnableToStart(
    modifier: Modifier = Modifier,
    appName: String,
    isBroadcastError: Boolean,
    isProxyError: Boolean,
) {
  val context = LocalContext.current
  val errType =
      remember(
          isBroadcastError,
          isProxyError,
          context,
      ) {
        if (isBroadcastError && isProxyError) {
          context.getString(R.string.trouble_err_type_both)
        } else if (isProxyError) {
          context.getString(R.string.trouble_err_type_proxy)
        } else {
          context.getString(R.string.trouble_err_type_broadcast)
        }
      }

  Column(
      modifier = modifier.padding(horizontal = MaterialTheme.keylines.content),
  ) {
    Text(
        text = stringResource(R.string.trouble_title, appName),
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.titleLarge,
    )
    Text(
        modifier = Modifier.padding(bottom = MaterialTheme.keylines.content),
        text = stringResource(R.string.trouble_description, errType),
        fontWeight = FontWeight.W700,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.error,
    )

    Text(
        modifier = Modifier.padding(bottom = MaterialTheme.keylines.baseline),
        text = stringResource(R.string.trouble_double_check),
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.W700,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    if (isBroadcastError) {
      Text(
          modifier = Modifier.padding(bottom = MaterialTheme.keylines.baseline),
          text = stringResource(R.string.trouble_broadcast_wifi_on),
          style = MaterialTheme.typography.bodyLarge,
      )

      Text(
          modifier = Modifier.padding(bottom = MaterialTheme.keylines.baseline),
          text = stringResource(R.string.trouble_broadcast_wifi_not_connected),
          style = MaterialTheme.typography.bodyLarge,
      )

      Text(
          modifier = Modifier.padding(bottom = MaterialTheme.keylines.baseline),
          text = stringResource(R.string.trouble_broadcast_wifi_restart),
          style = MaterialTheme.typography.bodyLarge,
      )

      Text(
          modifier = Modifier.padding(bottom = MaterialTheme.keylines.baseline),
          text = stringResource(R.string.trouble_broadcast_password_length),
          style = MaterialTheme.typography.bodyLarge,
      )

      Text(
          modifier = Modifier.padding(bottom = MaterialTheme.keylines.baseline),
          text = stringResource(R.string.trouble_broadcast_ssid_name),
          style = MaterialTheme.typography.bodyLarge,
      )
    }

    if (isProxyError) {
      Text(
          modifier = Modifier.padding(bottom = MaterialTheme.keylines.baseline),
          text = stringResource(R.string.trouble_proxy_already_used),
          style = MaterialTheme.typography.bodyLarge,
      )

      Text(
          modifier = Modifier.padding(bottom = MaterialTheme.keylines.baseline),
          text = stringResource(R.string.trouble_proxy_port),
          style = MaterialTheme.typography.bodyLarge,
      )
    }
  }
}
