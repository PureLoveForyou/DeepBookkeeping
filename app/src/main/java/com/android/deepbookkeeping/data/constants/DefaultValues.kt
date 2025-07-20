package com.android.deepbookkeeping.data.constants

import com.android.deepbookkeeping.R
import com.android.deepbookkeeping.data.local.entity.Category

object DefaultValues {
    val defaultIncomeCategory = Category(
        id = 1,
        name = "薪资",
        iconResourceId = R.drawable.ic_money_pile,
        type = Constants.TRANSACTION_INCOME,
        isDefault = true
    )
    val defaultExpenseCategory = Category(
        id = 9,
        name = "消费",
        iconResourceId = R.drawable.ic_money_bag,
        type = Constants.TRANSACTION_EXPENSE,
        isDefault = true
    )
    val defaultCategories = listOf(
        // 收入类别
        defaultIncomeCategory,
        Category(
            id = 2,
            name = "理财",
            iconResourceId = R.drawable.ic_money_in_hand,
            type = Constants.TRANSACTION_INCOME,
            isDefault = true
        ),
        Category(
            id = 3,
            name = "红包",
            iconResourceId = R.drawable.ic_pocket_money,
            type = Constants.TRANSACTION_INCOME,
            isDefault = true
        ),
        Category(
            id = 4,
            name = "转账",
            iconResourceId = R.drawable.ic_transer,
            type = Constants.TRANSACTION_INCOME,
            isDefault = true
        ),
        Category(
            id = 5,
            name = "收款",
            iconResourceId = R.drawable.ic_receive_money_mobile,
            type = Constants.TRANSACTION_INCOME,
            isDefault = true
        ),
        Category(
            id = 6,
            name = "借入",
            iconResourceId = R.drawable.ic_borrow,
            type = Constants.TRANSACTION_INCOME,
            isDefault = true
        ),
        Category(
            id = 7,
            name = "其他",
            iconResourceId = R.drawable.ic_more,
            type = Constants.TRANSACTION_INCOME,
            isDefault = true
        ),
        Category(
            id = 8,
            name = "添加",
            iconResourceId = R.drawable.ic_plus,
            type = Constants.TRANSACTION_INCOME,
            isDefault = true
        ),
        // 支出类别
        defaultExpenseCategory,
        Category(
            id = 10,
            name = "餐饮",
            iconResourceId = R.drawable.ic_dinning_hall,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 11,
            name = "购物",
            iconResourceId = R.drawable.ic_trolley,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 12,
            name = "住房",
            iconResourceId = R.drawable.ic_house,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 13,
            name = "交通",
            iconResourceId = R.drawable.ic_car,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 14,
            name = "通讯",
            iconResourceId = R.drawable.ic_phone,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 15,
            name = "娱乐",
            iconResourceId = R.drawable.ic_console,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 16,
            name = "医疗",
            iconResourceId = R.drawable.ic_first_aid_kit,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 17,
            name = "教育",
            iconResourceId = R.drawable.ic_education,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 18,
            name = "红包",
            iconResourceId = R.drawable.ic_pocket_money,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 19,
            name = "转账",
            iconResourceId = R.drawable.ic_transer,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 20,
            name = "旅行",
            iconResourceId = R.drawable.ic_luggage,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 21,
            name = "投资",
            iconResourceId = R.drawable.ic_investment,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 22,
            name = "借出",
            iconResourceId = R.drawable.ic_pay,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 23,
            name = "还款",
            iconResourceId = R.drawable.ic_credit_card,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 24,
            name = "美容",
            iconResourceId = R.drawable.ic_lipstick,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 25,
            name = "亲子",
            iconResourceId = R.drawable.ic_family,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 26,
            name = "社交",
            iconResourceId = R.drawable.ic_social_media,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 27,
            name = "宠物",
            iconResourceId = R.drawable.ic_pet,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 28,
            name = "快递",
            iconResourceId = R.drawable.ic_express_delivery,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 29,
            name = "其他",
            iconResourceId = R.drawable.ic_more,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        ),
        Category(
            id = 30,
            name = "添加",
            iconResourceId = R.drawable.ic_plus,
            type = Constants.TRANSACTION_EXPENSE,
            isDefault = true
        )
    )
}