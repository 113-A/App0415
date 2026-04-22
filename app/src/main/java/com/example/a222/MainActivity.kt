package com.example.a222

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
//import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight

import java.text.NumberFormat


import com.example.a222.ui.theme._222Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface {
                    DrinkOrderApp()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DrinkOrderPreview() {
    DrinkOrderApp()
}

@Composable
fun DrinkOrderApp() {

    var selectedDrink by remember { mutableStateOf("珍珠奶茶") }
    var selectedSugar by remember { mutableStateOf("半糖") }
    var selectedIce by remember { mutableStateOf("正常冰") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // ⭐ 防止小螢幕爆掉
            .padding(horizontal = 16.dp, vertical = 24.dp), // ⭐ 上下空間加大
        verticalArrangement = Arrangement.spacedBy(20.dp), // ⭐ 間距加大
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(80.dp)) // ⭐ 上方留白

        // 🧋 飲料選擇（可滑動更穩）
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()), // ⭐ 防爆
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DrinkButton("珍珠奶茶", selectedDrink, R.drawable.boba) { selectedDrink = it }
            DrinkButton("綠茶", selectedDrink, R.drawable.green_tea) { selectedDrink = it }
            DrinkButton("紅茶", selectedDrink, R.drawable.black_tea) { selectedDrink = it }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🍬 甜度（修正排版）
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "甜度選擇：",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(92.dp) // ⭐ 對齊關鍵
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OptionButton("全糖", selectedSugar) { selectedSugar = it }
                OptionButton("半糖", selectedSugar) { selectedSugar = it }
                OptionButton("無糖", selectedSugar) { selectedSugar = it }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🧊 冰塊（統一風格）
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "冰塊選擇：",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(90.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OptionButton("正常冰", selectedIce) { selectedIce = it }
                OptionButton("少冰", selectedIce) { selectedIce = it }
                OptionButton("去冰", selectedIce) { selectedIce = it }
            }
        }

        Spacer(modifier = Modifier.height(24.dp)) // ⭐ 中間留白

        // ✅ 按鈕
        Button(
            onClick = {
                result =
                    if (selectedDrink.isNotEmpty() && selectedSugar.isNotEmpty() && selectedIce.isNotEmpty())
                        "你點的是：$selectedDrink + $selectedSugar + $selectedIce"
                    else
                        "請選擇完整訂單"
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), // ⭐ 按鈕高度更好看
            shape = RoundedCornerShape(12.dp) // ⭐ 商用風格
        ) {
            Text("確認訂單")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 📄 結果
        if (result.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE3F2FD)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = result,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp)) // ⭐ 底部安全空間
    }
}

@Composable
fun DrinkButton(
    name: String,
    selected: String,
    imageRes: Int,
    onClick: (String) -> Unit
) {
    val isSelected = name == selected

    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable { onClick(name) },
        shape = RoundedCornerShape(12.dp),
        border =
            if (isSelected)
                BorderStroke(2.dp, Color(0xFF1976D2))
            else null
    ) {

        Box {

            // 🖼️ 圖片
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            // 🏷️ 文字（直接疊）
            Text(
                text = name,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-1).dp) // ⭐ 往上移一點（數值可調）
                    .background(
                        Color.White.copy(alpha = 0.85f),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun OptionButton(
    text: String,
    selected: String,
    onClick: (String) -> Unit
) {
    val isSelected = text== selected // 是否選擇

    Button(
        onClick = { onClick(text) }, // 點擊更新狀態
        shape = RoundedCornerShape(0.dp), // ⭐ 完全沒有圓角
        colors = ButtonDefaults.buttonColors(
            containerColor =
                if (isSelected)
                    Color(0xFF1976D2) // 選中：藍色
                else
                    Color.LightGray // 未選：灰色
        )
    ) {
        Text(text, color = Color.White)
    }
}





