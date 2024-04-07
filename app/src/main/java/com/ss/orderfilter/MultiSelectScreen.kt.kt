package com.ss.orderfilter

import android.text.TextUtils
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MultiSelectScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column {
            // show filter options
            FilterOptions(filterOptions)

            FilterList(filterOptions)
        }
    }
}
@Composable
fun FilterOptions(filterOptions: List<FilterOption>) {
    LazyRow(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 8.dp)) {
        items(filterOptions) { option ->
            FilterChip(
                filterOption = option,
                onChipClick = {
                    chipChangeListener(it, !it.selected)
                    OrderData.printList(filterOptions)
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilterList(filterOption: List<FilterOption>) {


    val selectedOptions = ArrayList<String>()
    LazyColumn {
        filterOption.forEach { option ->

            if (option.selected) {

                if (!selectedOptions.contains(option.option))
                    selectedOptions.add(option.option)

                stickyHeader {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = TextUtils.join(", ", selectedOptions),
                            modifier = Modifier
                                .fillMaxWidth(),

                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                items(option.dateUtil) {

                    OrderItem(it )
                }
            }
        }
    }

}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OrderItem(order: DateUtil) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Image(
            painter = painterResource(R.drawable.test),
            contentDescription = "Order Image",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(text = order.name, fontWeight = FontWeight.Bold)
            Text(text = order.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = order.price, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Date: ${order.displayDate}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
        }
    }
}
val filterOptions = OrderData.getFilter(OrderData.generateMonthlyStatements())
fun chipChangeListener(item: FilterOption, checked: Boolean) =
    filterOptions.find { it.option == item.option }?.let { task ->
        task.selected = checked
    }
