package com.example.unitconverter


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}


@SuppressLint("DefaultLocale")
@Composable
fun UnitConverter(){

    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf(0.00) }
    var inputUnit by remember { mutableStateOf("Select") }
    var outputUnit by remember { mutableStateOf("Select") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    var iConverterFactor by remember {mutableStateOf(0.00)}
    var oConverterFactor by remember {mutableStateOf(0.00)}

    fun convertUnits(){
        //Convert commas to dots (because of american format) then make it Double anyways
        var inputValueDouble = inputValue
        inputValueDouble = inputValueDouble.replace(oldValue = ",", newValue = ".")
        inputValueDouble.toDoubleOrNull() ?: 0.00
        outputValue = (inputValueDouble.toDouble() * iConverterFactor * 100 / oConverterFactor) / 100
    }

    // Main Column
    Column(
        Modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        // Tittle
        Text(
            text = "Unit Converter"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input number from User
        OutlinedTextField(
            value = inputValue,
            label = {Text("Enter Value")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            onValueChange = {
                inputValue = it
                if (iConverterFactor != 00.0 && oConverterFactor != 00.0 && inputValue != "") convertUnits() else outputValue = 00.0
            }

        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selects Row
        Row {

            // First Select Box
            Box {
                Button(
                    onClick = {
                        iExpanded = !iExpanded
                    },
                    enabled = true
                ) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }

                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false}) {
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            inputUnit = "Meters"
                            iExpanded = false
                            iConverterFactor = 1.00
                            if (oConverterFactor != 00.0 && inputValue != "") convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            inputUnit = "Centimeters"
                            iExpanded = false
                            iConverterFactor = 0.01
                            if (oConverterFactor != 00.0 && inputValue != "") convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Millimeters") },
                        onClick = {
                            inputUnit = "Millimeters"
                            iExpanded = false
                            iConverterFactor = 0.001
                            if ( oConverterFactor != 00.0 && inputValue != "") convertUnits()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Second Select Box
            Box {
                Button(
                    onClick = {
                        oExpanded = !oExpanded
                    },
                    enabled = true
                ) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }


                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false}) {
                    DropdownMenuItem(
                        text = {Text("Meters")},
                        onClick = {
                            outputUnit = "Meters"
                            oExpanded = false
                            oConverterFactor = 1.00
                            if (iConverterFactor != 00.0 && inputValue != "") convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = {Text("Centimeters")},
                        onClick = {
                            outputUnit = "Centimeters"
                            oExpanded = false
                            oConverterFactor = 0.01
                            if (iConverterFactor != 00.0 && inputValue != "") convertUnits()
                        },
                    )
                    DropdownMenuItem(
                        text = {Text("Millimeters")},
                        onClick = {
                            outputUnit = "Millimeters"
                            oExpanded = false
                            oConverterFactor = 0.001
                            if (iConverterFactor != 00.0 && inputValue != "") convertUnits()
                        }
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Result Box
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.70f)
                .border(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                ),
                    shape = RoundedCornerShape(8.dp)
                )
                .height(32.dp)
                .padding(4.dp),
            contentAlignment = Alignment.CenterStart,
        ){
            Text(
                "Result: ${if (outputValue != 0.00) String.format("%.3f", outputValue).replace(oldValue = ",", ".") else ""}"
            )

        }

    }
}





@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}