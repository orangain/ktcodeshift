package ktcodeshift

import ktast.ast.Node

fun kotlinFile(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    packageDirective: Node.PackageDirective? = null,
    importDirectives: List<Node.ImportDirective> = listOf(),
    declarations: List<Node.Declaration> = listOf(),
    tag: Any? = null
) = Node.KotlinFile(
    annotationSets = annotationSets,
    packageDirective = packageDirective,
    importDirectives = importDirectives,
    declarations = declarations,
    tag = tag
)

fun kotlinScript(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    packageDirective: Node.PackageDirective? = null,
    importDirectives: List<Node.ImportDirective> = listOf(),
    expressions: List<Node.Expression> = listOf(),
    tag: Any? = null
) = Node.KotlinScript(
    annotationSets = annotationSets,
    packageDirective = packageDirective,
    importDirectives = importDirectives,
    expressions = expressions,
    tag = tag
)

fun packageDirective(names: List<Node.Expression.NameExpression> = listOf(), tag: Any? = null) =
    Node.PackageDirective(names = names, tag = tag)

fun importDirective(
    names: List<Node.Expression.NameExpression> = listOf(),
    aliasName: Node.Expression.NameExpression? = null,
    tag: Any? = null
) = Node.ImportDirective(names = names, aliasName = aliasName, tag = tag)

fun forStatement(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    loopParam: Node.LambdaParam,
    inKeyword: Node.Keyword.In = Node.Keyword.In(),
    loopRange: Node.Expression,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    body: Node.Expression,
    tag: Any? = null
) = Node.Statement.ForStatement(
    lPar = lPar,
    loopParam = loopParam,
    inKeyword = inKeyword,
    loopRange = loopRange,
    rPar = rPar,
    body = body,
    tag = tag
)

fun whileStatement(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    condition: Node.Expression,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    body: Node.Expression,
    tag: Any? = null
) = Node.Statement.WhileStatement(lPar = lPar, condition = condition, rPar = rPar, body = body, tag = tag)

fun doWhileStatement(
    body: Node.Expression,
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    condition: Node.Expression,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    tag: Any? = null
) = Node.Statement.DoWhileStatement(body = body, lPar = lPar, condition = condition, rPar = rPar, tag = tag)

fun constructorClassParent(
    type: Node.Type.SimpleType,
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    args: List<Node.ValueArg> = listOf(),
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    tag: Any? = null
) = Node.Declaration.ClassOrObject.ConstructorClassParent(type = type, lPar = lPar, args = args, rPar = rPar, tag = tag)

fun delegationClassParent(type: Node.Type, expression: Node.Expression, tag: Any? = null) =
    Node.Declaration.ClassOrObject.DelegationClassParent(type = type, expression = expression, tag = tag)

fun typeClassParent(type: Node.Type, tag: Any? = null) =
    Node.Declaration.ClassOrObject.TypeClassParent(type = type, tag = tag)

fun classBody(
    enumEntries: List<Node.Declaration.ClassOrObject.ClassBody.EnumEntry> = listOf(),
    declarations: List<Node.Declaration> = listOf(),
    tag: Any? = null
) = Node.Declaration.ClassOrObject.ClassBody(enumEntries = enumEntries, declarations = declarations, tag = tag)

fun enumEntry(
    modifiers: List<Node.Modifier> = listOf(),
    name: Node.Expression.NameExpression,
    lPar: Node.Keyword.LPar? = null,
    args: List<Node.ValueArg> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    classBody: Node.Declaration.ClassOrObject.ClassBody? = null,
    tag: Any? = null
) = Node.Declaration.ClassOrObject.ClassBody.EnumEntry(
    modifiers = modifiers,
    name = name,
    lPar = if (args.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    args = args,
    rPar = if (args.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    classBody = classBody,
    tag = tag
)

fun initializer(block: Node.Expression.BlockExpression, tag: Any? = null) =
    Node.Declaration.ClassOrObject.ClassBody.Initializer(block = block, tag = tag)

fun secondaryConstructor(
    modifiers: List<Node.Modifier> = listOf(),
    constructorKeyword: Node.Keyword.Constructor = Node.Keyword.Constructor(),
    lPar: Node.Keyword.LPar? = null,
    params: List<Node.FunctionParam> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    delegationCall: Node.Expression.CallExpression? = null,
    block: Node.Expression.BlockExpression? = null,
    tag: Any? = null
) = Node.Declaration.ClassOrObject.ClassBody.SecondaryConstructor(
    modifiers = modifiers,
    constructorKeyword = constructorKeyword,
    lPar = if (params.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    params = params,
    rPar = if (params.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    delegationCall = delegationCall,
    block = block,
    tag = tag
)

fun classDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    declarationKeyword: Node.Declaration.ClassDeclaration.ClassOrInterfaceKeyword,
    name: Node.Expression.NameExpression,
    lAngle: Node.Keyword.Less? = null,
    typeParams: List<Node.TypeParam> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    primaryConstructor: Node.Declaration.ClassDeclaration.PrimaryConstructor? = null,
    parents: List<Node.Declaration.ClassOrObject.ClassParent> = listOf(),
    typeConstraintSet: Node.PostModifier.TypeConstraintSet? = null,
    body: Node.Declaration.ClassOrObject.ClassBody? = null,
    tag: Any? = null
) = Node.Declaration.ClassDeclaration(
    modifiers = modifiers,
    declarationKeyword = declarationKeyword,
    name = name,
    lAngle = if (typeParams.isNotEmpty()) lAngle ?: Node.Keyword.Less() else lAngle,
    typeParams = typeParams,
    rAngle = if (typeParams.isNotEmpty()) rAngle ?: Node.Keyword.Greater() else rAngle,
    primaryConstructor = primaryConstructor,
    parents = parents,
    typeConstraintSet = typeConstraintSet,
    body = body,
    tag = tag
)

fun primaryConstructor(
    modifiers: List<Node.Modifier> = listOf(),
    constructorKeyword: Node.Keyword.Constructor? = null,
    lPar: Node.Keyword.LPar? = null,
    params: List<Node.FunctionParam> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    tag: Any? = null
) = Node.Declaration.ClassDeclaration.PrimaryConstructor(
    modifiers = modifiers,
    constructorKeyword = constructorKeyword,
    lPar = if (params.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    params = params,
    rPar = if (params.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    tag = tag
)

fun objectDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    declarationKeyword: Node.Keyword.Object = Node.Keyword.Object(),
    name: Node.Expression.NameExpression? = null,
    parents: List<Node.Declaration.ClassOrObject.ClassParent> = listOf(),
    body: Node.Declaration.ClassOrObject.ClassBody? = null,
    tag: Any? = null
) = Node.Declaration.ObjectDeclaration(
    modifiers = modifiers,
    declarationKeyword = declarationKeyword,
    name = name,
    parents = parents,
    body = body,
    tag = tag
)

fun functionDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    lAngle: Node.Keyword.Less? = null,
    typeParams: List<Node.TypeParam> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    receiverType: Node.Type? = null,
    name: Node.Expression.NameExpression? = null,
    lPar: Node.Keyword.LPar? = null,
    params: List<Node.FunctionParam> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    returnType: Node.Type? = null,
    postModifiers: List<Node.PostModifier> = listOf(),
    body: Node.Expression? = null,
    tag: Any? = null
) = Node.Declaration.FunctionDeclaration(
    modifiers = modifiers,
    lAngle = if (typeParams.isNotEmpty()) lAngle ?: Node.Keyword.Less() else lAngle,
    typeParams = typeParams,
    rAngle = if (typeParams.isNotEmpty()) rAngle ?: Node.Keyword.Greater() else rAngle,
    receiverType = receiverType,
    name = name,
    lPar = if (params.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    params = params,
    rPar = if (params.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    returnType = returnType,
    postModifiers = postModifiers,
    body = body,
    tag = tag
)

fun propertyDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    valOrVarKeyword: Node.Keyword.ValOrVarKeyword,
    lAngle: Node.Keyword.Less? = null,
    typeParams: List<Node.TypeParam> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    receiverType: Node.Type? = null,
    lPar: Node.Keyword.LPar? = null,
    variables: List<Node.Variable> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    typeConstraintSet: Node.PostModifier.TypeConstraintSet? = null,
    initializerExpression: Node.Expression? = null,
    delegateExpression: Node.Expression? = null,
    accessors: List<Node.Declaration.PropertyDeclaration.Accessor> = listOf(),
    tag: Any? = null
) = Node.Declaration.PropertyDeclaration(
    modifiers = modifiers,
    valOrVarKeyword = valOrVarKeyword,
    lAngle = if (typeParams.isNotEmpty()) lAngle ?: Node.Keyword.Less() else lAngle,
    typeParams = typeParams,
    rAngle = if (typeParams.isNotEmpty()) rAngle ?: Node.Keyword.Greater() else rAngle,
    receiverType = receiverType,
    lPar = if (variables.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    variables = variables,
    rPar = if (variables.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    typeConstraintSet = typeConstraintSet,
    initializerExpression = initializerExpression,
    delegateExpression = delegateExpression,
    accessors = accessors,
    tag = tag
)

fun getter(
    modifiers: List<Node.Modifier> = listOf(),
    lPar: Node.Keyword.LPar? = null,
    rPar: Node.Keyword.RPar? = null,
    type: Node.Type? = null,
    postModifiers: List<Node.PostModifier> = listOf(),
    body: Node.Expression? = null,
    tag: Any? = null
) = Node.Declaration.PropertyDeclaration.Getter(
    modifiers = modifiers,
    lPar = if (body != null) lPar ?: Node.Keyword.LPar() else lPar,
    rPar = if (body != null) rPar ?: Node.Keyword.RPar() else rPar,
    type = type,
    postModifiers = postModifiers,
    body = body,
    tag = tag
)

fun setter(
    modifiers: List<Node.Modifier> = listOf(),
    lPar: Node.Keyword.LPar? = null,
    params: List<Node.LambdaParam> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    postModifiers: List<Node.PostModifier> = listOf(),
    body: Node.Expression? = null,
    tag: Any? = null
) = Node.Declaration.PropertyDeclaration.Setter(
    modifiers = modifiers,
    lPar = if (params.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    params = params,
    rPar = if (params.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    postModifiers = postModifiers,
    body = body,
    tag = tag
)

fun typeAliasDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    name: Node.Expression.NameExpression,
    lAngle: Node.Keyword.Less? = null,
    typeParams: List<Node.TypeParam> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    type: Node.Type,
    tag: Any? = null
) = Node.Declaration.TypeAliasDeclaration(
    modifiers = modifiers,
    name = name,
    lAngle = if (typeParams.isNotEmpty()) lAngle ?: Node.Keyword.Less() else lAngle,
    typeParams = typeParams,
    rAngle = if (typeParams.isNotEmpty()) rAngle ?: Node.Keyword.Greater() else rAngle,
    type = type,
    tag = tag
)

fun parenthesizedType(
    modifiers: List<Node.Modifier> = listOf(),
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    innerType: Node.Type,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    tag: Any? = null
) = Node.Type.ParenthesizedType(modifiers = modifiers, lPar = lPar, innerType = innerType, rPar = rPar, tag = tag)

fun nullableType(
    modifiers: List<Node.Modifier> = listOf(),
    innerType: Node.Type,
    questionMark: Node.Keyword.Question = Node.Keyword.Question(),
    tag: Any? = null
) = Node.Type.NullableType(modifiers = modifiers, innerType = innerType, questionMark = questionMark, tag = tag)

fun simpleType(
    modifiers: List<Node.Modifier> = listOf(),
    qualifiers: List<Node.Type.SimpleType.SimpleTypeQualifier> = listOf(),
    name: Node.Expression.NameExpression,
    lAngle: Node.Keyword.Less? = null,
    typeArgs: List<Node.TypeArg> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    tag: Any? = null
) = Node.Type.SimpleType(
    modifiers = modifiers,
    qualifiers = qualifiers,
    name = name,
    lAngle = lAngle,
    typeArgs = typeArgs,
    rAngle = rAngle,
    tag = tag
)

fun simpleTypeQualifier(
    name: Node.Expression.NameExpression,
    lAngle: Node.Keyword.Less? = null,
    typeArgs: List<Node.TypeArg> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    tag: Any? = null
) = Node.Type.SimpleType.SimpleTypeQualifier(
    name = name,
    lAngle = lAngle,
    typeArgs = typeArgs,
    rAngle = rAngle,
    tag = tag
)

fun dynamicType(modifiers: List<Node.Modifier> = listOf(), tag: Any? = null) =
    Node.Type.DynamicType(modifiers = modifiers, tag = tag)

fun functionType(
    modifiers: List<Node.Modifier> = listOf(),
    contextReceiver: Node.ContextReceiver? = null,
    receiverType: Node.Type? = null,
    lPar: Node.Keyword.LPar? = null,
    params: List<Node.Type.FunctionType.FunctionTypeParam> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    returnType: Node.Type,
    tag: Any? = null
) = Node.Type.FunctionType(
    modifiers = modifiers,
    contextReceiver = contextReceiver,
    receiverType = receiverType,
    lPar = if (params.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    params = params,
    rPar = if (params.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    returnType = returnType,
    tag = tag
)

fun functionTypeParam(name: Node.Expression.NameExpression? = null, type: Node.Type, tag: Any? = null) =
    Node.Type.FunctionType.FunctionTypeParam(name = name, type = type, tag = tag)

fun ifExpression(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    condition: Node.Expression,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    body: Node.Expression,
    elseBody: Node.Expression? = null,
    tag: Any? = null
) = Node.Expression.IfExpression(
    lPar = lPar,
    condition = condition,
    rPar = rPar,
    body = body,
    elseBody = elseBody,
    tag = tag
)

fun tryExpression(
    block: Node.Expression.BlockExpression,
    catchClauses: List<Node.Expression.TryExpression.CatchClause> = listOf(),
    finallyBlock: Node.Expression.BlockExpression? = null,
    tag: Any? = null
) = Node.Expression.TryExpression(block = block, catchClauses = catchClauses, finallyBlock = finallyBlock, tag = tag)

fun catchClause(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    params: List<Node.FunctionParam> = listOf(),
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    block: Node.Expression.BlockExpression,
    tag: Any? = null
) = Node.Expression.TryExpression.CatchClause(lPar = lPar, params = params, rPar = rPar, block = block, tag = tag)

fun whenExpression(
    whenKeyword: Node.Keyword.When = Node.Keyword.When(),
    subject: Node.Expression.WhenExpression.WhenSubject? = null,
    branches: List<Node.Expression.WhenExpression.WhenBranch> = listOf(),
    tag: Any? = null
) = Node.Expression.WhenExpression(whenKeyword = whenKeyword, subject = subject, branches = branches, tag = tag)

fun whenSubject(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    variable: Node.Variable? = null,
    expression: Node.Expression,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    tag: Any? = null
) = Node.Expression.WhenExpression.WhenSubject(
    lPar = lPar,
    annotationSets = annotationSets,
    variable = variable,
    expression = expression,
    rPar = rPar,
    tag = tag
)

fun conditionalWhenBranch(
    conditions: List<Node.Expression.WhenExpression.WhenCondition> = listOf(),
    arrow: Node.Keyword.Arrow = Node.Keyword.Arrow(),
    body: Node.Expression,
    tag: Any? = null
) = Node.Expression.WhenExpression.ConditionalWhenBranch(conditions = conditions, arrow = arrow, body = body, tag = tag)

fun elseWhenBranch(arrow: Node.Keyword.Arrow = Node.Keyword.Arrow(), body: Node.Expression, tag: Any? = null) =
    Node.Expression.WhenExpression.ElseWhenBranch(arrow = arrow, body = body, tag = tag)

fun expressionWhenCondition(expression: Node.Expression, tag: Any? = null) =
    Node.Expression.WhenExpression.ExpressionWhenCondition(expression = expression, tag = tag)

fun rangeWhenCondition(
    operator: Node.Expression.WhenExpression.WhenConditionRangeOperator,
    expression: Node.Expression,
    tag: Any? = null
) = Node.Expression.WhenExpression.RangeWhenCondition(operator = operator, expression = expression, tag = tag)

fun typeWhenCondition(
    operator: Node.Expression.WhenExpression.WhenConditionTypeOperator,
    type: Node.Type,
    tag: Any? = null
) = Node.Expression.WhenExpression.TypeWhenCondition(operator = operator, type = type, tag = tag)

fun throwExpression(expression: Node.Expression, tag: Any? = null) =
    Node.Expression.ThrowExpression(expression = expression, tag = tag)

fun returnExpression(
    label: Node.Expression.NameExpression? = null,
    expression: Node.Expression? = null,
    tag: Any? = null
) = Node.Expression.ReturnExpression(label = label, expression = expression, tag = tag)

fun continueExpression(label: Node.Expression.NameExpression? = null, tag: Any? = null) =
    Node.Expression.ContinueExpression(label = label, tag = tag)

fun breakExpression(label: Node.Expression.NameExpression? = null, tag: Any? = null) =
    Node.Expression.BreakExpression(label = label, tag = tag)

fun blockExpression(statements: List<Node.Statement> = listOf(), tag: Any? = null) =
    Node.Expression.BlockExpression(statements = statements, tag = tag)

fun blockExpression(vararg statements: Node.Statement) = blockExpression(statements.toList())
fun callExpression(
    calleeExpression: Node.Expression,
    lAngle: Node.Keyword.Less? = null,
    typeArgs: List<Node.TypeArg> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    lPar: Node.Keyword.LPar? = null,
    args: List<Node.ValueArg> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    lambdaArg: Node.Expression.CallExpression.LambdaArg? = null,
    tag: Any? = null
) = Node.Expression.CallExpression(
    calleeExpression = calleeExpression,
    lAngle = if (typeArgs.isNotEmpty()) lAngle ?: Node.Keyword.Less() else lAngle,
    typeArgs = typeArgs,
    rAngle = if (typeArgs.isNotEmpty()) rAngle ?: Node.Keyword.Greater() else rAngle,
    lPar = if (args.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    args = args,
    rPar = if (args.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    lambdaArg = lambdaArg,
    tag = tag
)

fun lambdaArg(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    label: Node.Expression.NameExpression? = null,
    expression: Node.Expression.LambdaExpression,
    tag: Any? = null
) = Node.Expression.CallExpression.LambdaArg(
    annotationSets = annotationSets,
    label = label,
    expression = expression,
    tag = tag
)

fun lambdaExpression(
    params: List<Node.LambdaParam> = listOf(),
    arrow: Node.Keyword.Arrow? = null,
    statements: List<Node.Statement> = listOf(),
    tag: Any? = null
) = Node.Expression.LambdaExpression(params = params, arrow = arrow, statements = statements, tag = tag)

fun binaryExpression(
    lhs: Node.Expression,
    operator: Node.Expression.BinaryExpression.BinaryOperator,
    rhs: Node.Expression,
    tag: Any? = null
) = Node.Expression.BinaryExpression(lhs = lhs, operator = operator, rhs = rhs, tag = tag)

fun prefixUnaryExpression(
    operator: Node.Expression.UnaryExpression.UnaryOperator,
    expression: Node.Expression,
    tag: Any? = null
) = Node.Expression.PrefixUnaryExpression(operator = operator, expression = expression, tag = tag)

fun postfixUnaryExpression(
    expression: Node.Expression,
    operator: Node.Expression.UnaryExpression.UnaryOperator,
    tag: Any? = null
) = Node.Expression.PostfixUnaryExpression(expression = expression, operator = operator, tag = tag)

fun binaryTypeExpression(
    lhs: Node.Expression,
    operator: Node.Expression.BinaryTypeExpression.BinaryTypeOperator,
    rhs: Node.Type,
    tag: Any? = null
) = Node.Expression.BinaryTypeExpression(lhs = lhs, operator = operator, rhs = rhs, tag = tag)

fun callableReferenceExpression(
    lhs: Node.Expression? = null,
    questionMarks: List<Node.Keyword.Question> = listOf(),
    rhs: Node.Expression.NameExpression,
    tag: Any? = null
) = Node.Expression.CallableReferenceExpression(lhs = lhs, questionMarks = questionMarks, rhs = rhs, tag = tag)

fun classLiteralExpression(
    lhs: Node.Expression,
    questionMarks: List<Node.Keyword.Question> = listOf(),
    tag: Any? = null
) = Node.Expression.ClassLiteralExpression(lhs = lhs, questionMarks = questionMarks, tag = tag)

fun parenthesizedExpression(innerExpression: Node.Expression, tag: Any? = null) =
    Node.Expression.ParenthesizedExpression(innerExpression = innerExpression, tag = tag)

fun stringLiteralExpression(
    entries: List<Node.Expression.StringLiteralExpression.StringEntry> = listOf(),
    raw: Boolean = false,
    tag: Any? = null
) = Node.Expression.StringLiteralExpression(entries = entries, raw = raw, tag = tag)

fun literalStringEntry(text: String, tag: Any? = null) =
    Node.Expression.StringLiteralExpression.LiteralStringEntry(text = text, tag = tag)

fun escapeStringEntry(text: String, tag: Any? = null) =
    Node.Expression.StringLiteralExpression.EscapeStringEntry(text = text, tag = tag)

fun templateStringEntry(expression: Node.Expression, short: Boolean = false, tag: Any? = null) =
    Node.Expression.StringLiteralExpression.TemplateStringEntry(expression = expression, short = short, tag = tag)

fun booleanLiteralExpression(text: String, tag: Any? = null) =
    Node.Expression.BooleanLiteralExpression(text = text, tag = tag)

fun characterLiteralExpression(text: String, tag: Any? = null) =
    Node.Expression.CharacterLiteralExpression(text = text, tag = tag)

fun integerLiteralExpression(text: String, tag: Any? = null) =
    Node.Expression.IntegerLiteralExpression(text = text, tag = tag)

fun realLiteralExpression(text: String, tag: Any? = null) =
    Node.Expression.RealLiteralExpression(text = text, tag = tag)

fun nullLiteralExpression(tag: Any? = null) = Node.Expression.NullLiteralExpression(tag = tag)
fun objectLiteralExpression(declaration: Node.Declaration.ObjectDeclaration, tag: Any? = null) =
    Node.Expression.ObjectLiteralExpression(declaration = declaration, tag = tag)

fun collectionLiteralExpression(expressions: List<Node.Expression> = listOf(), tag: Any? = null) =
    Node.Expression.CollectionLiteralExpression(expressions = expressions, tag = tag)

fun thisExpression(label: Node.Expression.NameExpression? = null, tag: Any? = null) =
    Node.Expression.ThisExpression(label = label, tag = tag)

fun superExpression(typeArg: Node.TypeArg? = null, label: Node.Expression.NameExpression? = null, tag: Any? = null) =
    Node.Expression.SuperExpression(typeArg = typeArg, label = label, tag = tag)

fun nameExpression(text: String, tag: Any? = null) = Node.Expression.NameExpression(text = text, tag = tag)
fun labeledExpression(label: Node.Expression.NameExpression, statement: Node.Statement, tag: Any? = null) =
    Node.Expression.LabeledExpression(label = label, statement = statement, tag = tag)

fun annotatedExpression(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    statement: Node.Statement,
    tag: Any? = null
) = Node.Expression.AnnotatedExpression(annotationSets = annotationSets, statement = statement, tag = tag)

fun indexedAccessExpression(expression: Node.Expression, indices: List<Node.Expression> = listOf(), tag: Any? = null) =
    Node.Expression.IndexedAccessExpression(expression = expression, indices = indices, tag = tag)

fun anonymousFunctionExpression(function: Node.Declaration.FunctionDeclaration, tag: Any? = null) =
    Node.Expression.AnonymousFunctionExpression(function = function, tag = tag)

fun typeParam(
    modifiers: List<Node.Modifier> = listOf(),
    name: Node.Expression.NameExpression,
    type: Node.Type? = null,
    tag: Any? = null
) = Node.TypeParam(modifiers = modifiers, name = name, type = type, tag = tag)

fun functionParam(
    modifiers: List<Node.Modifier> = listOf(),
    valOrVarKeyword: Node.Keyword.ValOrVarKeyword? = null,
    name: Node.Expression.NameExpression,
    type: Node.Type? = null,
    defaultValue: Node.Expression? = null,
    tag: Any? = null
) = Node.FunctionParam(
    modifiers = modifiers,
    valOrVarKeyword = valOrVarKeyword,
    name = name,
    type = type,
    defaultValue = defaultValue,
    tag = tag
)

fun lambdaParam(
    lPar: Node.Keyword.LPar? = null,
    variables: List<Node.Variable> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    destructType: Node.Type? = null,
    tag: Any? = null
) = Node.LambdaParam(lPar = lPar, variables = variables, rPar = rPar, destructType = destructType, tag = tag)

fun variable(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    name: Node.Expression.NameExpression,
    type: Node.Type? = null,
    tag: Any? = null
) = Node.Variable(annotationSets = annotationSets, name = name, type = type, tag = tag)

fun typeArg(modifiers: List<Node.Modifier> = listOf(), type: Node.Type, tag: Any? = null) =
    Node.TypeArg(modifiers = modifiers, type = type, tag = tag)

fun valueArg(
    name: Node.Expression.NameExpression? = null,
    spreadOperator: Node.Keyword.Asterisk? = null,
    expression: Node.Expression,
    tag: Any? = null
) = Node.ValueArg(name = name, spreadOperator = spreadOperator, expression = expression, tag = tag)

fun contextReceiver(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    receiverTypes: List<Node.Type> = listOf(),
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    tag: Any? = null
) = Node.ContextReceiver(lPar = lPar, receiverTypes = receiverTypes, rPar = rPar, tag = tag)

fun annotationSet(
    target: Node.Modifier.AnnotationSet.AnnotationTarget? = null,
    lBracket: Node.Keyword.LBracket? = null,
    annotations: List<Node.Modifier.AnnotationSet.Annotation> = listOf(),
    rBracket: Node.Keyword.RBracket? = null,
    tag: Any? = null
) = Node.Modifier.AnnotationSet(
    target = target,
    lBracket = if (annotations.size >= 2) lBracket ?: Node.Keyword.LBracket() else lBracket,
    annotations = annotations,
    rBracket = if (annotations.size >= 2) rBracket ?: Node.Keyword.RBracket() else rBracket,
    tag = tag
)

fun annotation(
    type: Node.Type.SimpleType,
    lPar: Node.Keyword.LPar? = null,
    args: List<Node.ValueArg> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    tag: Any? = null
) = Node.Modifier.AnnotationSet.Annotation(
    type = type,
    lPar = if (args.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    args = args,
    rPar = if (args.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    tag = tag
)

fun typeConstraintSet(
    constraints: List<Node.PostModifier.TypeConstraintSet.TypeConstraint> = listOf(),
    tag: Any? = null
) = Node.PostModifier.TypeConstraintSet(constraints = constraints, tag = tag)

fun typeConstraint(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    name: Node.Expression.NameExpression,
    type: Node.Type,
    tag: Any? = null
) = Node.PostModifier.TypeConstraintSet.TypeConstraint(
    annotationSets = annotationSets,
    name = name,
    type = type,
    tag = tag
)

fun contract(
    lBracket: Node.Keyword.LBracket = Node.Keyword.LBracket(),
    contractEffects: List<Node.Expression> = listOf(),
    rBracket: Node.Keyword.RBracket = Node.Keyword.RBracket(),
    tag: Any? = null
) = Node.PostModifier.Contract(lBracket = lBracket, contractEffects = contractEffects, rBracket = rBracket, tag = tag)

fun whitespace(text: String, tag: Any? = null) = Node.Extra.Whitespace(text = text, tag = tag)
fun comment(text: String, tag: Any? = null) = Node.Extra.Comment(text = text, tag = tag)
fun semicolon(tag: Any? = null) = Node.Extra.Semicolon(tag = tag)
fun trailingComma(tag: Any? = null) = Node.Extra.TrailingComma(tag = tag)
