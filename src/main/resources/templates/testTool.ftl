<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>商城模板</title>
    <link href="/resources/images/favicon.ico" rel="icon" type="image/x-icon"/>
    <link rel="stylesheet" href="/resources/css/jquery-ui.css">
    <link rel="stylesheet" href="/resources/css/style.css">
    <link rel="stylesheet" href="/resources/css/tab.css">
    <link rel="stylesheet" href="/resources/js/mask/mask.css">
    <link rel="stylesheet" href="/resources/js/JsonParser/JsonParser.css">
</head>
<body>
<div id="tabs" class="ui-widget-content">
    <div class="tab">
        <button class="tablinks" onclick="openTab(1)">api</button>
        <button class="tablinks" onclick="openTab(2)">management</button>
    </div>
    <div id="tabs-1" style="width: 2000px;display: none">
        <div style="float: left; width: 400px;">
            <h2>用户(customer)</h2>
            <div>
                <strong>[发送验证码]</strong><br/>
                手机号码:<input class="textbox" type="text" id="customer-captcha-phone" style="width: 100px;"
                            value="13600000000"/><br/>
                <input type="button" value="注册" onclick="customerModule.captcha()"/><br>
            </div>
            <div>
                <strong>[注册]</strong><br/>
                手机号码:<input class="textbox" type="text" id="customer-create-phone" style="width: 100px;"
                            value="13600000000"/><br/>
                验证码:<input class="textbox" type="text" id="customer-create-captcha" style="width: 100px;"
                          value=""/><br/>
                密码:<input class="textbox" type="text" id="customer-create-loginPassword" style="width: 100px;"
                            value="123456"/><br/>
                <input type="button" value="注册" onclick="customerModule.create()"/><br>
            </div>
            <div>
                <strong>[登录]</strong><br/>
                手机:<input class="textbox" type="text" id="customer-a-login-phone" style="width: 100px;"
                          value="13600000000"/>
                密码:<input class="textbox" type="text" id="customer-a-login-loginPassword" style="width: 100px;"
                          value="123456"/>
                <input type="button" value="登录" onclick="customerModule.login()"/><br>
            </div>
            <div>
                <strong>[修改登录密码]</strong><br/>
                旧密码:<input class="textbox" type="text" id="customer-resetLogPwd-a-originPwd" style="width: 100px;"
                           value="123456"/>
                新密码:<input class="textbox" type="text" id="customer-resetLogPwd-a-newPwd" style="width: 100px;"
                           value="123456"/>
                <input type="button" value="修改" onclick="customerModule.resetLogPwd()"/><br>
            </div>
            <div>
                <strong>[当前用户]</strong><br/>
                <input type="button" value="当前用户" onclick="customerModule.current()"/><br>
            </div>
            <div>
                <strong>[修改信息]</strong><br/>
                用户名:<input class="textbox" type="text" id="customer-update-a-name" style="width: 100px;"
                           value="test"/><br>
                <input type="button" value="修改" onclick="customerModule.update()"/><br>
            </div>
            <div>
                <strong>[登出]</strong><br/>
                <input type="button" value="登出" onclick="customerModule.logout()"/><br>
            </div>

            <h2>用户地址(customerAddress)</h2>
            <div>
                <strong>[列表]</strong><br/>
                <input type="button" value="列表" onclick="customerAddressModule.list()"/><br>
            </div>
            <div>
                <strong>[详情]</strong><br/>
                id:<input class="textbox" type="text" id="address-detail-id" style="width: 100px;"
                           value=""/><br>
                <input type="button" value="详情" onclick="customerAddressModule.detail()"/><br>
            </div>
            <div>
                <strong>[创建]</strong><br/>
                联系人:<input class="textbox" type="text" id="address-create-contact" style="width: 100px;"
                          value="test"/>
                联系电话:<input class="textbox" type="text" id="address-create-phone" style="width: 100px;"
                           value="13600000000"/><br>
                省:<input class="textbox" type="text" id="address-create-province" style="width: 100px;"
                            value="福建省"/>
                市:<input class="textbox" type="text" id="address-create-city" style="width: 100px;"
                         value="厦门市"/><br>
                区:<input class="textbox" type="text" id="address-create-county" style="width: 100px;"
                         value="思明区"/>
                地址:<input class="textbox" type="text" id="address-create-address" style="width: 100px;"
                         value="中山公园"/><br>
                主要地址:<select class="textbox" id="address-create-primary" style="width: 100px;">
                <option value="0">否</option>
                <option value="1" selected>是</option>
            </select><br>
                <input type="button" value="创建" onclick="customerAddressModule.create()"/><br>
            </div>
            <div>
                <strong>[编辑]</strong><br/>
                id:<input class="textbox" type="text" id="address-update-id" style="width: 100px;"
                           value=""/><br>
                联系人:<input class="textbox" type="text" id="address-update-contact" style="width: 100px;"
                           value="test"/>
                联系电话:<input class="textbox" type="text" id="address-update-phone" style="width: 100px;"
                            value="13600000000"/><br>
                省:<input class="textbox" type="text" id="address-update-province" style="width: 100px;"
                         value="福建省"/>
                市:<input class="textbox" type="text" id="address-update-city" style="width: 100px;"
                         value="厦门市"/><br>
                区:<input class="textbox" type="text" id="address-update-county" style="width: 100px;"
                         value="思明区"/>
                地址:<input class="textbox" type="text" id="address-update-address" style="width: 100px;"
                          value="中山公园"/><br>
                主要地址:<select class="textbox" id="address-update-primary" style="width: 100px;">
                <option value="0">否</option>
                <option value="1" selected>是</option>
            </select><br>
                <input type="button" value="编辑" onclick="customerAddressModule.update()"/><br>
            </div>
            <div>
                <strong>[删除]</strong><br/>
                id:<input class="textbox" type="text" id="address-delete-id" style="width: 100px;"
                          value=""/><br>
                <input type="button" value="删除" onclick="customerAddressModule.delete()"/><br>
            </div>
        </div>
        <div style="float: left; width: 400px;">
            <h2>商品品类(goods_category)</h2>
            <div>
                <strong>[列表]</strong><br/>
                <input type="button" value="列表" onclick="goodsCategoryModule.listApi()"/><br>
            </div>
            <h2>商品(goods)</h2>
            <div>
                <strong>[列表]</strong><br/>
                content:<input class="textbox" type="text" id="goods-api-list-content" style="width: 100px;"
                               value=""/>
                categoryId1:<input class="textbox" type="text" id="goods-api-list-categoryId1" style="width: 100px;"
                                   value=""/><br>
                categoryId2:<input class="textbox" type="text" id="goods-api-list-categoryId2" style="width: 100px;"
                                   value=""/>
                categoryId3:<input class="textbox" type="text" id="goods-api-list-categoryId3" style="width: 100px;"
                                   value=""/><br>
                pageIndex:<input class="textbox" type="text" id="goods-api-list-pageIndex" style="width: 100px;"
                                 value="0"/>
                pageSize:<input class="textbox" type="text" id="goods-api-list-pageSize" style="width: 100px;"
                                value="10"/><br>
                <input type="button" value="列表" onclick="goodsModule.listApi()"/><br>
            </div>
            <div>
                <strong>[详情]</strong><br/>
                id:<input class="textbox" type="text" id="goods-api-detail-id" style="width: 100px;"
                          value=""/>
                <input type="button" value="详情" onclick="goodsModule.detailApi()"/><br>
            </div>
            <div>
                <strong>[获取商品图片]</strong><br/>
                fileName:<input class="textbox" type="text" id="goods-api-image-fileName" style="width: 100px;"
                          value="1.jpg"/>
                <input type="button" value="图片" onclick="goodsModule.imageApi()"/><br>
            </div>
            <div>
                <h2>购物车(cart)</h2>
                <div>
                    <strong>[总额及数量]</strong><br/>
                    <input type="button" value="获取" onclick="cartModule.info()"/><br>
                </div>
                <div>
                    <strong>[详情]</strong><br/>
                    <input type="button" value="获取" onclick="cartModule.detail()"/><br>
                </div>
                <div>
                    <strong>[添加]</strong><br/>
                    商品id:<input class="textbox" type="text" id="cart-add-goodsId" style="width: 100px;"
                                value="1"/><br>
                    skuId:<input class="textbox" type="text" id="cart-add-unitId" style="width: 100px;"
                                               value=""/><br>
                    数量:<input class="textbox" type="text" id="cart-add-count" style="width: 100px;"
                              value="1"/><br>
                    <input type="button" value="添加" onclick="cartModule.add()"/><br>
                </div>
                <div>
                    <strong>[编辑]</strong><br/>
                    购物车itemId:<input class="textbox" type="text" id="cart-edit-itemId" style="width: 100px;"
                                     value="1"/><br>
                    数量:<input class="textbox" type="text" id="cart-edit-count" style="width: 100px;"
                              value="2"/><br>
                    <input type="button" value="编辑" onclick="cartModule.edit()"/><br>
                </div>
                <div>
                    <strong>[删除商品]</strong><br/>
                    购物车itemId:<input class="textbox" type="text" id="cart-delete-id" style="width: 100px;"
                                     value=""/><br>
                    <input type="button" value="删除" onclick="cartModule.deleteById()"/><br>
                </div>
                <div>
                    <strong>[结算]</strong><br/>
                    购物车itemId:<input class="textbox" type="text" id="cart-checkout-id1" style="width: 100px;"
                                     value=""/><br>
                    购物车itemId:<input class="textbox" type="text" id="cart-checkout-id2" style="width: 100px;"
                                     value=""/><br>
                    <input type="button" value="结算" onclick="cartModule.checkout()"/><br>
                </div>
            </div>
            <h2>订单(order)</h2>
            <div>
                <strong>[列表]</strong><br/>
                content:<input class="textbox" type="text" id="order-api-list-content" style="width: 100px;"
                               value=""/>
                status:<select class="textbox" id="order-api-list-status" style="width: 100px;">
                <option value="">all</option>
                <option value="-1">取消</option>
                <option value="0">创建</option>
                <option value="1">已支付</option>
                <option value="2">已确认</option>
            </select><br>
                pageIndex:<input class="textbox" type="text" id="order-api-list-pageIndex" style="width: 100px;"
                                 value="0"/>
                pageSize:<input class="textbox" type="text" id="order-api-list-pageSize" style="width: 100px;"
                                value="10"/><br>
                <input type="button" value="列表" onclick="orderModule.listApi()"/><br>
            </div>
            <div>
                <strong>[详情]</strong><br/>
                id:<input class="textbox" type="text" id="order-api-detail-id" style="width: 100px;"
                          value=""/>
                <input type="button" value="详情" onclick="orderModule.detailApi()"/><br>
            </div>
            <div>
                <strong>[创建]</strong><br/>
                amount:<input class="textbox" type="text" id="order-api-create-amount" style="width: 100px;"
                               value=""/>
                recharge:<select class="textbox" id="order-api-create-recharge" style="width: 100px;">
                <option value="0">vipCard</option>
                <option value="1">normal</option>
                <option value="2">recharge</option>
            </select><br>
                remark(unnecessary):<input class="textbox" type="text" id="order-api-create-remark" style="width: 100px;"
                                 value=""/><br>

                has item?:<select class="textbox" id="order-api-create-hasItem" style="width: 100px;">
                <option value="1">yes</option>
                <option value="2">no</option>
            </select><br>

                orderItem:<br>
                goodsId:<input class="textbox" type="text" id="order-api-create-goodsId" style="width: 100px;"
                              value=""/>
                goodsName:<input class="textbox" type="text" id="order-api-create-goodsName" style="width: 100px;"
                                           value=""/><br>
                price:<input class="textbox" type="text" id="order-api-create-price" style="width: 100px;"
                               value=""/>
                count:<input class="textbox" type="text" id="order-api-create-count" style="width: 100px;"
                                 value=""/><br>
                <input type="button" value="列表" onclick="orderModule.createApi()"/><br>
            </div>
            <div>
                <strong>[确认支付]</strong><br/>
                id:<input class="textbox" type="text" id="order-api-paid-id" style="width: 100px;"
                          value=""/>
                <input type="button" value="详情" onclick="orderModule.paid()"/><br>
            </div>
            <h2>系统参数(system_params)</h2>
            <div>
                <strong>[列表]</strong><br/>
                <input type="button" value="列表" onclick="systemApiParamsModule.list()"/><br>
            </div>
        </div>
    </div>
    <div id="tabs-2" style="width: 2000px;display: block">
        <div style="float: left; width: 400px;">
            <h2>管理员(user)</h2>
            <div>
                <strong>[登录]</strong><br/>
                管理员:<input class="textbox" type="text" id="login-m-name" style="width: 100px;"
                           value="admin"/>
                密码:<input class="textbox" type="text" id="login-m-password" style="width: 100px;"
                          value="123456"/>
                <input type="button" value="登录" onclick="commonModule.login()"/><br>
            </div>
            <div>
                <strong>[修改密码]</strong><br/>
                旧密码:<input class="textbox" type="text" id="resetPwd-m-originPwd" style="width: 100px;"
                           value=""/>
                新密码:<input class="textbox" type="text" id="resetPwd-m-newPwd" style="width: 100px;"
                           value=""/>
                <input type="button" value="修改" onclick="commonModule.resetPwd()"/><br>
            </div>
            <div>
                <strong>[当前管理员]</strong><br/>
                <input type="button" value="获取" onclick="commonModule.currentUser()"/><br>
            </div>
            <div>
                <strong>[登出]</strong><br/>
                <input type="button" value="登出" onclick="commonModule.logout()"/><br>
            </div>

            <h2>系统参数(system_params)</h2>
            <div>
                <strong>[列表]</strong><br/>
                <input type="button" value="列表" onclick="systemParamsModule.list()"/><br>
            </div>
            <div>
                <strong>[修改参数]</strong><br/>
                id:<input class="textbox" type="text" id="systemParam-update-id" style="width: 100px;"
                          value=""/>
                value:<input class="textbox" type="text" id="systemParam-update-value" style="width: 100px;"
                             value=""/><br>
                <input type="button" value="修改" onclick="systemParamsModule.update()"/><br>
            </div>
            <h2>商品品类(goods_category)</h2>
            <div>
                <strong>[列表]</strong><br/>
                <input type="button" value="列表" onclick="goodsCategoryModule.list()"/><br>
            </div>
            <div>
                <strong>[创建]</strong><br/>
                name:<input class="textbox" type="text" id="goodsCategory-create-name" style="width: 100px;"
                            value="测试品类一级"/>
                level:<input class="textbox" type="text" id="goodsCategory-create-level" style="width: 100px;"
                            value="1"/>
                parentId:<input class="textbox" type="text" id="goodsCategory-create-parentId" style="width: 100px;"
                            value="0"/>
                <input type="button" value="创建" onclick="goodsCategoryModule.create()"/><br>
            </div>
            <div>
                <strong>[修改]</strong><br/>
                id:<input class="textbox" type="text" id="goodsCategory-update-id" style="width: 100px;"
                          value="1"/>
                name:<input class="textbox" type="text" id="goodsCategory-update-name" style="width: 100px;"
                            value=""/><br>
                level:<input class="textbox" type="text" id="goodsCategory-update-level" style="width: 100px;"
                             value="1"/>
                parentId:<input class="textbox" type="text" id="goodsCategory-update-parentId" style="width: 100px;"
                                value="0"/>
                <input type="button" value="修改" onclick="goodsCategoryModule.update()"/><br>
            </div>
            <div>
                <strong>[上架/下架]</strong><br/>
                id:<input class="textbox" type="text" id="goodsCategory-status-id" style="width: 100px;"
                          value=""/>
                status:<select class="textbox" id="goodsCategory-status-status" style="width: 100px;">
                <option value="0">disable</option>
                <option value="1">enable</option>
            </select><br>
                <input type="button" value="修改" onclick="goodsCategoryModule.updateStatus()"/><br>
            </div>
            <div>
                <strong>[删除]</strong><br/>
                id:<input class="textbox" type="text" id="goodsCategory-delete-id" style="width: 100px;"
                          value=""/>
                <input type="button" value="删除" onclick="goodsCategoryModule.deleteById()"/><br>
            </div>
        </div>
        <div style="float: left; width: 800px;">
            <h2>商品(goods)</h2>
            <div>
                <strong>[列表]</strong><br/>
                content:<input class="textbox" type="text" id="goods-list-content" style="width: 100px;"
                               value=""/>
                categoryId1:<input class="textbox" type="text" id="goods-list-categoryId1" style="width: 100px;"
                                   value=""/><br>
                status:<select class="textbox" id="goods-list-status" style="width: 100px;">
                <option value="">all</option>
                <option value="0">disable</option>
                <option value="1">enable</option>
            </select><br>
                pageIndex:<input class="textbox" type="text" id="goods-list-pageIndex" style="width: 100px;"
                                 value="0"/>
                pageSize:<input class="textbox" type="text" id="goods-list-pageSize" style="width: 100px;"
                                value="10"/><br>
                <input type="button" value="列表" onclick="goodsModule.list()"/><br>
            </div>
            <div>
                <strong>[详情]</strong><br/>
                id:<input class="textbox" type="text" id="goods-detail-id" style="width: 100px;"
                          value=""/>
                <input type="button" value="详情" onclick="goodsModule.detail()"/><br>
            </div>
            <div>
                <strong>[创建商品]</strong><br/>
                名称:<input class="textbox" type="text" id="goods-create-name" style="width: 100px;"
                          value="测试"/>
                品类1id:<input class="textbox" type="text" id="goods-create-categoryId1" style="width: 100px;"
                             value="3"/>
                品类1:<input class="textbox" type="text" id="goods-create-category1" style="width: 100px;"
                           value="测试品类1"/> &ensp;
                品类2id:<input class="textbox" type="text" id="goods-create-categoryId2" style="width: 100px;"
                             value="4"/>
                品类2:<input class="textbox" type="text" id="goods-create-category2" style="width: 100px;"
                           value="测试品类2"/><br>
                头图url:<input class="textbox" type="text" id="goods-create-bannerUrl" style="width: 100px;"
                             value="image.jpg"/>
                图片url:<input class="textbox" type="text" id="goods-create-imagesUrl" style="width: 100px;"
                             value="image.jpg,image1.jpg"/> &ensp;
                描述:<input class="textbox" type="text" id="goods-create-remarks" style="width: 100px;"
                          value="<html>xxx</html>"/><br/>
                sku1: &ensp;
                临时id:<input class="textbox" type="text" id="goods-create-sku1-tempId" style="width: 100px;"
                            value="1"/> &ensp;
                标题:<input class="textbox" type="text" id="goods-create-sku1-title" style="width: 100px;"
                          value="颜色"/><br>
                子项1: &ensp;
                临时id:<input class="textbox" type="text" id="goods-create-sku11-tempId" style="width: 100px;"
                            value="2"/>
                临时parentId:<input class="textbox" type="text" id="goods-create-sku11-parentTempId" style="width: 100px;"
                                  value="1"/> &ensp;
                标题:<input class="textbox" type="text" id="goods-create-sku11-title" style="width: 100px;"
                          value="红色"/><br>
                <input class="textbox" type="checkbox" id="goods-create-sku11-primary" checked style="width: 10px;"/>首选sku<br>

                子项2: &ensp;
                临时id:<input class="textbox" type="text" id="goods-create-sku12-tempId" style="width: 100px;"
                            value="3"/>
                临时parentId:<input class="textbox" type="text" id="goods-create-sku12-parentTempId" style="width: 100px;"
                                  value="1"/> &ensp;
                标题:<input class="textbox" type="text" id="goods-create-sku12-title" style="width: 100px;"
                          value="蓝色"/>
                <input class="textbox" type="checkbox" id="goods-create-sku12-primary" style="width: 10px;"/>首选sku<br>


                sku2: &ensp;
                临时id:<input class="textbox" type="text" id="goods-create-sku2-tempId" style="width: 100px;"
                            value="4"/> &ensp;
                标题:<input class="textbox" type="text" id="goods-create-sku2-title" style="width: 100px;"
                          value="尺码"/>
                子项1: &ensp;
                临时id:<input class="textbox" type="text" id="goods-create-sku21-tempId" style="width: 100px;"
                            value="5"/>
                临时parentId:<input class="textbox" type="text" id="goods-create-sku21-parentTempId" style="width: 100px;"
                                  value="4"/> &ensp;
                标题:<input class="textbox" type="text" id="goods-create-sku21-title" style="width: 100px;"
                          value="35"/>
                <input class="textbox" type="checkbox" id="goods-create-sku21-primary" checked style="width: 10px;"/>首选sku<br>

                子项2: &ensp;
                临时id:<input class="textbox" type="text" id="goods-create-sku22-tempId" style="width: 100px;"
                            value="6"/>
                临时parentId:<input class="textbox" type="text" id="goods-create-sku22-parentTempId" style="width: 100px;"
                                  value="4"/> &ensp;
                标题:<input class="textbox" type="text" id="goods-create-sku22-title" style="width: 100px;"
                          value="36"/>
                <input class="textbox" type="checkbox" id="goods-create-sku22-primary" style="width: 10px;"/>首选sku<br>

                unit:<br>
                unit1: &ensp;
                skuIds:<input class="textbox" type="text" id="goods-create-unit1-specIds" style="width: 100px;"
                              value="2,5"/><br>

                标题:<input class="textbox" type="text" id="goods-create-unit1-title" style="width: 100px;"
                          value="红色35"/>
                库存:<input class="textbox" type="text" id="goods-create-unit1-stocks" style="width: 100px;"
                          value="100"/>
                价格:<input class="textbox" type="text" id="goods-create-unit1-price" style="width: 100px;"
                          value="10"/>
                促销价:<input class="textbox" type="text" id="goods-create-unit1-promotionPrice" style="width: 100px;"
                           value="1"/><br>
                运费:<input class="textbox" type="text" id="goods-create-unit1-shippingCost" style="width: 100px;"
                          value="0"/>
                销量:<input class="textbox" type="text" id="goods-create-unit1-salesVolume" style="width: 100px;"
                          value="0"/>
                图片:<input class="textbox" type="text" id="goods-create-unit1-imageUrl" style="width: 100px;"
                          value="test.jpg"/>
                <input class="textbox" type="checkbox" id="goods-create-unit1-primary" checked style="width: 10px;"/>首选unit<br>

                unit2: &ensp;
                skuIds:<input class="textbox" type="text" id="goods-create-unit2-specIds" style="width: 100px;"
                              value="2,6"/><br>
                标题:<input class="textbox" type="text" id="goods-create-unit2-title" style="width: 100px;"
                          value="红色36"/>
                库存:<input class="textbox" type="text" id="goods-create-unit2-stocks" style="width: 100px;"
                          value="100"/>
                价格:<input class="textbox" type="text" id="goods-create-unit2-price" style="width: 100px;"
                          value="10"/>
                促销价:<input class="textbox" type="text" id="goods-create-unit2-promotionPrice" style="width: 100px;"
                           value="1"/><br>
                运费:<input class="textbox" type="text" id="goods-create-unit2-shippingCost" style="width: 100px;"
                          value="0"/>
                销量:<input class="textbox" type="text" id="goods-create-unit2-salesVolume" style="width: 100px;"
                          value="0"/>
                图片:<input class="textbox" type="text" id="goods-create-unit2-imageUrl" style="width: 100px;"
                          value="test.jpg"/>
                <input class="textbox" type="checkbox" id="goods-create-unit2-primary" style="width: 10px;"/>首选unit<br>

                unit3: &ensp;
                skuIds:<input class="textbox" type="text" id="goods-create-unit3-specIds" style="width: 100px;"
                              value="3,5"/><br>
                标题:<input class="textbox" type="text" id="goods-create-unit3-title" style="width: 100px;"
                          value="蓝色35"/>
                库存:<input class="textbox" type="text" id="goods-create-unit3-stocks" style="width: 100px;"
                          value="100"/>
                价格:<input class="textbox" type="text" id="goods-create-unit3-price" style="width: 100px;"
                          value="10"/>
                促销价:<input class="textbox" type="text" id="goods-create-unit3-promotionPrice" style="width: 100px;"
                           value="1"/><br>
                运费:<input class="textbox" type="text" id="goods-create-unit3-shippingCost" style="width: 100px;"
                          value="0"/>
                销量:<input class="textbox" type="text" id="goods-create-unit3-salesVolume" style="width: 100px;"
                          value="0"/>
                图片:<input class="textbox" type="text" id="goods-create-unit3-imageUrl" style="width: 100px;"
                          value="test.jpg"/>
                <input class="textbox" type="checkbox" id="goods-create-unit3-primary" style="width: 10px;"/>首选unit<br>

                unit4: &ensp;
                skuIds:<input class="textbox" type="text" id="goods-create-unit4-specIds" style="width: 100px;"
                              value="3,6"/><br>
                标题:<input class="textbox" type="text" id="goods-create-unit4-title" style="width: 100px;"
                          value="蓝色36"/>
                库存:<input class="textbox" type="text" id="goods-create-unit4-stocks" style="width: 100px;"
                          value="100"/>
                价格:<input class="textbox" type="text" id="goods-create-unit4-price" style="width: 100px;"
                          value="10"/>
                促销价:<input class="textbox" type="text" id="goods-create-unit4-promotionPrice" style="width: 100px;"
                           value="1"/><br>
                运费:<input class="textbox" type="text" id="goods-create-unit4-shippingCost" style="width: 100px;"
                          value="0"/>
                销量:<input class="textbox" type="text" id="goods-create-unit4-salesVolume" style="width: 100px;"
                          value="0"/>
                图片:<input class="textbox" type="text" id="goods-create-unit4-imageUrl" style="width: 100px;"
                          value="test.jpg"/>
                <input class="textbox" type="checkbox" id="goods-create-unit4-primary" style="width: 10px;"/>首选unit<br>

                <input type="button" value="创建" onclick="goodsModule.create()"/><br>
            </div>
            <div >
                <strong>[编辑商品]</strong><br/>
                id:<input class="textbox" type="text" id="goods-update-id" style="width: 100px;"
                          value="5"/>
                名称:<input class="textbox" type="text" id="goods-update-name" style="width: 100px;"
                          value="测试"/>
                品类1id:<input class="textbox" type="text" id="goods-update-categoryId1" style="width: 100px;"
                             value="1"/>
                品类1:<input class="textbox" type="text" id="goods-update-category1" style="width: 100px;"
                           value="测试品类1"/> &ensp;
                品类2id:<input class="textbox" type="text" id="goods-update-categoryId2" style="width: 100px;"
                             value="2"/>
                品类2:<input class="textbox" type="text" id="goods-update-category2" style="width: 100px;"
                           value="测试品类1"/><br>
                头图url:<input class="textbox" type="text" id="goods-update-bannerUrl" style="width: 100px;"
                             value="image.jpg"/>
                图片url:<input class="textbox" type="text" id="goods-update-imagesUrl" style="width: 100px;"
                             value="image.jpg,image1.jpg"/> &ensp;
                描述:<input class="textbox" type="text" id="goods-update-remarks" style="width: 100px;"
                          value="<html>xxx</html>"/><br>
                sku:<br>
                sku1: &ensp;
                id:<input class="textbox" type="text" id="goods-update-sku1-id" style="width: 100px;"
                          value="7"/> &ensp;
                标题:<input class="textbox" type="text" id="goods-update-sku1-title" style="width: 100px;"
                          value="修改颜色"/><br>
                子项1: &ensp;
                id:<input class="textbox" type="text" id="goods-update-sku11-id" style="width: 100px;"
                          value="9"/>&ensp;
                标题:<input class="textbox" type="text" id="goods-update-sku11-title" style="width: 100px;"
                          value="修改红色"/>
                <input class="textbox" type="checkbox" id="goods-update-sku11-primary" checked style="width: 10px;"/>首选sku<br>

                子项2: &ensp;
                id:<input class="textbox" type="text" id="goods-update-sku12-id" style="width: 100px;"
                          value="10"/>&ensp;
                标题:<input class="textbox" type="text" id="goods-update-sku12-title" style="width: 100px;"
                          value="蓝色"/>
                <input class="textbox" type="checkbox" id="goods-update-sku12-primary" style="width: 10px;"/>首选sku<br>


                sku2: &ensp;
                id:<input class="textbox" type="text" id="goods-update-sku2-id" style="width: 100px;"
                          value="8"/> &ensp;
                标题:<input class="textbox" type="text" id="goods-update-sku2-title" style="width: 100px;"
                          value="修改尺码"/><br>
                子项1: &ensp;
                id:<input class="textbox" type="text" id="goods-update-sku21-id" style="width: 100px;"
                          value="11"/>&ensp;
                标题:<input class="textbox" type="text" id="goods-update-sku21-title" style="width: 100px;"
                          value="改35"/>
                <input class="textbox" type="checkbox" id="goods-update-sku21-primary" style="width: 10px;"/>首选sku<br>

                子项2: &ensp;
                临时id:<input class="textbox" type="text" id="goods-update-sku22-tempId" style="width: 100px;"
                            value="t_1"/>
                parentId:<input class="textbox" type="text" id="goods-update-sku22-parentId" style="width: 100px;"
                                value="8"/> &ensp;
                标题:<input class="textbox" type="text" id="goods-update-sku22-title" style="width: 100px;"
                          value="37"/>
                <input class="textbox" type="checkbox" id="goods-update-sku22-primary" checked style="width: 10px;"/>首选sku<br>

                unit:<br>
                unit1: &ensp;
                id:<input class="textbox" type="text" id="goods-update-unit1-id" style="width: 100px;"
                          value="1"/><br>
                标题:<input class="textbox" type="text" id="goods-update-unit1-title" style="width: 100px;"
                          value="红色35修改"/>
                库存:<input class="textbox" type="text" id="goods-update-unit1-stocks" style="width: 100px;"
                          value="100"/>
                价格:<input class="textbox" type="text" id="goods-update-unit1-price" style="width: 100px;"
                          value="10"/>
                运费:<input class="textbox" type="text" id="goods-update-unit1-shippingCost" style="width: 100px;"
                          value="0"/>
                销量:<input class="textbox" type="text" id="goods-update-unit1-salesVolume" style="width: 100px;"
                          value="0"/>
                图片:<input class="textbox" type="text" id="goods-update-unit1-imageUrl" style="width: 100px;"
                          value="test.jpg"/>
                <input class="textbox" type="checkbox" id="goods-update-unit1-primary" style="width: 10px;"/>首选unit<br>

                unit2: &ensp;
                skuIds:<input class="textbox" type="text" id="goods-update-unit2-specIds" style="width: 100px;"
                              value="9,t_1"/><br>
                标题:<input class="textbox" type="text" id="goods-update-unit2-title" style="width: 100px;"
                          value="修改红色37"/>
                库存:<input class="textbox" type="text" id="goods-update-unit2-stocks" style="width: 100px;"
                          value="100"/>
                价格:<input class="textbox" type="text" id="goods-update-unit2-price" style="width: 100px;"
                          value="10"/>
                运费:<input class="textbox" type="text" id="goods-update-unit2-shippingCost" style="width: 100px;"
                          value="0"/>
                销量:<input class="textbox" type="text" id="goods-update-unit2-salesVolume" style="width: 100px;"
                          value="0"/>
                图片:<input class="textbox" type="text" id="goods-update-unit2-imageUrl" style="width: 100px;"
                          value="test.jpg"/>
                <input class="textbox" type="checkbox" id="goods-update-unit2-primary" checked style="width: 10px;"/>首选unit<br>

                unit3: &ensp;
                id:<input class="textbox" type="text" id="goods-update-unit3-id" style="width: 100px;"
                          value="3"/><br>
                标题:<input class="textbox" type="text" id="goods-update-unit3-title" style="width: 100px;"
                          value="蓝色35测试"/>
                库存:<input class="textbox" type="text" id="goods-update-unit3-stocks" style="width: 100px;"
                          value="50"/>
                价格:<input class="textbox" type="text" id="goods-update-unit3-price" style="width: 100px;"
                          value="10"/>
                运费:<input class="textbox" type="text" id="goods-update-unit3-shippingCost" style="width: 100px;"
                          value="0"/>
                销量:<input class="textbox" type="text" id="goods-update-unit3-salesVolume" style="width: 100px;"
                          value="0"/>
                图片:<input class="textbox" type="text" id="goods-update-unit3-imageUrl" style="width: 100px;"
                          value="test.jpg"/>
                <input class="textbox" type="checkbox" id="goods-update-unit3-primary" style="width: 10px;"/>首选unit<br>

                unit4: &ensp;
                skuIds:<input class="textbox" type="text" id="goods-update-unit4-specIds" style="width: 100px;"
                              value="10,t_1"/><br>
                标题:<input class="textbox" type="text" id="goods-update-unit4-title" style="width: 100px;"
                          value="蓝色37"/>
                库存:<input class="textbox" type="text" id="goods-update-unit4-stocks" style="width: 100px;"
                          value="100"/>
                价格:<input class="textbox" type="text" id="goods-update-unit4-price" style="width: 100px;"
                          value="10"/>
                运费:<input class="textbox" type="text" id="goods-update-unit4-shippingCost" style="width: 100px;"
                          value="0"/>
                销量:<input class="textbox" type="text" id="goods-update-unit4-salesVolume" style="width: 100px;"
                          value="0"/>
                图片:<input class="textbox" type="text" id="goods-update-unit4-imageUrl" style="width: 100px;"
                          value="test.jpg"/>
                <input class="textbox" type="checkbox" id="goods-update-unit4-primary" style="width: 10px;"/>首选unit<br>

                <input type="button" value="编辑" onclick="goodsModule.update()"/><br>
            </div>
            <div>
                <strong>[上架]</strong><br/>
                id:<input class="textbox" type="text" id="goods-status-id" style="width: 100px;"
                          value=""/>
                status:<select class="textbox" id="goods-status-status" style="width: 100px;">
                <option value="0">disable</option>
                <option value="1">enable</option>
            </select><br>
                <input type="button" value="修改" onclick="goodsModule.updateStatus()"/><br>
            </div>
            <div>
                <strong>[删除]</strong><br/>
                id:<input class="textbox" type="text" id="goods-delete-id" style="width: 100px;"
                          value=""/>
                <input type="button" value="删除" onclick="goodsModule.deleteById()"/><br>
            </div>
        </div>
        <div style="float: left; width: 400px;">
            <h2>用户</h2>
            <strong>[用户列表]</strong><br/>
            搜索内容:<input class="textbox" type="text" id="customer-m-list-content" style="width: 100px;"
                      value=""/><br/>
            startIndex:<input class="textbox" type="text" id="customer-m-List-startIndex" style="width: 100px;"
                              value="0"/>
            pageSize:<input class="textbox" type="text" id="customer-m-List-pageSize" style="width: 100px;"
                            value="10"/><br>
            <input type="button" value="列表" onclick="customerMaModule.list()"/><br>
            <div>
                <strong>[详情]</strong><br/>
                id:<input class="textbox" type="text" id="customer-m-detail-id" style="width: 100px;"
                          value=""/><br/>
                <input type="button" value="详情" onclick="customerMaModule.detail()"/><br>
            </div>
            <h2>订单(order)</h2>
            <div>
                <strong>[列表]</strong><br/>
                content:<input class="textbox" type="text" id="order-list-content" style="width: 100px;"
                            value=""/>
                status:<select class="textbox" id="order-list-status" style="width: 100px;">
                <option value="">all</option>
                <option value="-1">取消</option>
                <option value="2">确认</option>
                <option value="1">待处理</option>
            </select><br>
                pageIndex:<input class="textbox" type="text" id="order-list-pageIndex" style="width: 100px;"
                                  value="0"/>
                pageSize:<input class="textbox" type="text" id="order-list-pageSize" style="width: 100px;"
                                value="10"/><br>
                <input type="button" value="列表" onclick="orderModule.list()"/><br>
            </div>
            <div>
                <strong>[详情]</strong><br/>
                id:<input class="textbox" type="text" id="order-detail-id" style="width: 100px;"
                               value=""/>
                <input type="button" value="详情" onclick="orderModule.detail()"/><br>
            </div>
            <div>
                <strong>[修改状态]</strong><br/>
                id:<input class="textbox" type="text" id="order-status-id" style="width: 100px;"
                               value=""/>
                status:<select class="textbox" id="order-status-status" style="width: 100px;">
                <option value="-1">取消</option>
                <option value="2">确认</option>
            </select><br>
                cancelReason(unnecessary):<input class="textbox" type="text" id="order-status-cancelReason" style="width: 100px;"
                                 value=""/><br>
                remarks(unnecessary):<input class="textbox" type="text" id="order-status-remarks" style="width: 100px;"
                                value=""/><br>
                <input type="button" value="修改" onclick="orderModule.updateStatus()"/><br>
            </div>
        </div>
    </div>
    <div style="clear: both;"></div>
</div>

<div style="margin-top: 7px; height:27px;" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
    <b>Requested URL: </b>
    <input type="text" id="requestedUrl" style="width:60%;"
           domain="${request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()?c}"/>
</div>

<div id="d_param" class="ui-tabs ui-corner-all">
    <div style="float:left;width:300px; margin:0 10px 10px 0;height: 450px;"
         class="ui-tabs ui-widget ui-widget-content ui-corner-all">
        <strong style="margin-left: 5px;">Requested Method:</strong>

        <lable id="requestedMethod"></lable>
        <br/><br/>
        <strong style="margin-left: 5px;">HTTP Header:</strong>
        <table id="tb_h_param">
        <#--<tr>
            <td>ClientId:</td>
            <td><input type="text" id="p_clientId"/></td>
        </tr>
        <tr>
            <td>SecretKey:</td>
            <td><input type="text" id="p_secretKey"/></td>
        </tr>-->
            <tr>
                <td colspan="2"><textarea id="p_data" rows="20" cols="35"></textarea></td>
            </tr>
        </table>
    </div>

    <div style="margin-left:315px; z-index:9999; position:relative;">

        <div id="resultShow"></div>
    </div>
</div>


</body>

<script src="/resources/js/jquery-1.10.2.js"></script>
<script src="/resources/js/jquery-ui.js"></script>
<script src="/resources/js/JsonParser/JsonParser.js"></script>
<script src="/resources/js/crypto/hmac-sha1.js"></script>
<script src="/resources/js/crypto/enc-base64-min.js"></script>
<script src="/resources/js/tool.js"></script>
<script src="/resources/js/mask/mask.js"></script>

</html>
