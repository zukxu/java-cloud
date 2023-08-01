---
title: java-cloud v1.0.0
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.11"

---

# java-cloud

> v1.0.0

# 微服务鉴权

## GET hello测试接口

GET /api/hello

### 请求参数

| 名称            | 位置     | 类型     | 必选 | 说明   |
|---------------|--------|--------|----|------|
| Authorization | header | string | 是  | none |

> 返回示例

### 返回结果

| 状态码 | 状态码含义                                                   | 说明 | 数据模型   |
|-----|---------------------------------------------------------|----|--------|
| 200 | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | 成功 | Inline |

### 返回数据结构

## POST token

POST /auth/oauth/token

> Body 请求参数

```yaml
username: macro
password: 123456
grant_type: password
client_id: client-app
client_secret: "123456"

```

### 请求参数

| 名称              | 位置   | 类型     | 必选 | 说明   |
|-----------------|------|--------|----|------|
| body            | body | object | 否  | none |
| » username      | body | string | 否  | none |
| » password      | body | string | 否  | none |
| » grant_type    | body | string | 是  | none |
| » client_id     | body | string | 是  | none |
| » client_secret | body | string | 是  | none |

> 返回示例

### 返回结果

| 状态码 | 状态码含义                                                   | 说明 | 数据模型   |
|-----|---------------------------------------------------------|----|--------|
| 200 | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | 成功 | Inline |

### 返回数据结构

## GET 当前用户信息

GET /api/user/currentUser

### 请求参数

| 名称            | 位置     | 类型     | 必选 | 说明   |
|---------------|--------|--------|----|------|
| Authorization | header | string | 否  | none |

> 返回示例

### 返回结果

| 状态码 | 状态码含义                                                   | 说明 | 数据模型   |
|-----|---------------------------------------------------------|----|--------|
| 200 | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | 成功 | Inline |

### 返回数据结构

# 数据模型

<h2 id="tocS_WorkFlowDTO">WorkFlowDTO</h2>

<a id="schemaworkflowdto"></a>
<a id="schema_WorkFlowDTO"></a>
<a id="tocSworkflowdto"></a>
<a id="tocsworkflowdto"></a>

```json
{
  "id": 0,
  "creatTime": "string",
  "creatTimeStr": null,
  "identifier": "string",
  "parentId": "string",
  "receiverUnit": "string",
  "processTime": "string",
  "processTimeStr": null,
  "processTimeStart": null,
  "processTimeEnd": null,
  "identySubType": "string",
  "identyDetail": "string",
  "identyType": "string",
  "title": "string",
  "content": "string",
  "state": "string",
  "receiverUnitName": "string",
  "procinstId": "string",
  "replyTime": "string",
  "bgnTime": null,
  "endTime": null,
  "creator": "string",
  "originUnit": "string",
  "originUnitName": null,
  "bak": null,
  "notAudit": "string",
  "identyTypeName": null,
  "identySubTypeName": null,
  "identyDetailName": null,
  "creatorID": null,
  "isSendMe": null,
  "improvementPoints": null,
  "uriPc": null,
  "timeOutFlag": null,
  "taskId": null,
  "policylabel": "string",
  "identifierList": null
}

```

### 属性

| 名称                | 类型      | 必选   | 约束   | 中文名 | 说明   |
|-------------------|---------|------|------|-----|------|
| id                | integer | true | none |     | none |
| creatTime         | string  | true | none |     | none |
| creatTimeStr      | null    | true | none |     | none |
| identifier        | string  | true | none |     | none |
| parentId          | string  | true | none |     | none |
| receiverUnit      | string  | true | none |     | none |
| processTime       | string  | true | none |     | none |
| processTimeStr    | null    | true | none |     | none |
| processTimeStart  | null    | true | none |     | none |
| processTimeEnd    | null    | true | none |     | none |
| identySubType     | string  | true | none |     | none |
| identyDetail      | string  | true | none |     | none |
| identyType        | string  | true | none |     | none |
| title             | string  | true | none |     | none |
| content           | string  | true | none |     | none |
| state             | string  | true | none |     | none |
| receiverUnitName  | string  | true | none |     | none |
| procinstId        | string  | true | none |     | none |
| replyTime         | string  | true | none |     | none |
| bgnTime           | null    | true | none |     | none |
| endTime           | null    | true | none |     | none |
| creator           | string  | true | none |     | none |
| originUnit        | string  | true | none |     | none |
| originUnitName    | null    | true | none |     | none |
| bak               | null    | true | none |     | none |
| notAudit          | string  | true | none |     | none |
| identyTypeName    | null    | true | none |     | none |
| identySubTypeName | null    | true | none |     | none |
| identyDetailName  | null    | true | none |     | none |
| creatorID         | null    | true | none |     | none |
| isSendMe          | null    | true | none |     | none |
| improvementPoints | null    | true | none |     | none |
| uriPc             | null    | true | none |     | none |
| timeOutFlag       | null    | true | none |     | none |
| taskId            | null    | true | none |     | none |
| policylabel       | string  | true | none |     | none |
| identifierList    | null    | true | none |     | none |

<h2 id="tocS_holidayVariables">holidayVariables</h2>

<a id="schemaholidayvariables"></a>
<a id="schema_holidayVariables"></a>
<a id="tocSholidayvariables"></a>
<a id="tocsholidayvariables"></a>

```json
{
  "employee": "string",
  "nrOfHolidays": 0,
  "description": "string"
}

```

### 属性

| 名称           | 类型      | 必选   | 约束   | 中文名 | 说明   |
|--------------|---------|------|------|-----|------|
| employee     | string  | true | none |     | 请假人  |
| nrOfHolidays | integer | true | none |     | 请假天数 |
| description  | string  | true | none |     | 请假理由 |

