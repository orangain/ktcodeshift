package ktcodeshift

import ktast.ast.Node
import ktast.ast.NodeSupplement

fun kotlinFile(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    packageDirective: Node.PackageDirective? = null,
    importDirectives: List<Node.ImportDirective> = listOf(),
    declarations: List<Node.Declaration> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.KotlinFile(
    annotationSets = annotationSets,
    packageDirective = packageDirective,
    importDirectives = importDirectives,
    declarations = declarations,
    supplement = supplement
)

fun kotlinScript(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    packageDirective: Node.PackageDirective? = null,
    importDirectives: List<Node.ImportDirective> = listOf(),
    expressions: List<Node.Expression> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.KotlinScript(
    annotationSets = annotationSets,
    packageDirective = packageDirective,
    importDirectives = importDirectives,
    expressions = expressions,
    supplement = supplement
)

fun packageDirective(
    names: List<Node.Expression.NameExpression> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.PackageDirective(names = names, supplement = supplement)

fun importDirective(
    names: List<Node.Expression.NameExpression> = listOf(),
    aliasName: Node.Expression.NameExpression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.ImportDirective(names = names, aliasName = aliasName, supplement = supplement)

fun forStatement(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    loopParameter: Node.LambdaParameter,
    inKeyword: Node.Keyword.In = Node.Keyword.In(),
    loopRange: Node.Expression,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    body: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Statement.ForStatement(
    lPar = lPar,
    loopParameter = loopParameter,
    inKeyword = inKeyword,
    loopRange = loopRange,
    rPar = rPar,
    body = body,
    supplement = supplement
)

fun whileStatement(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    condition: Node.Expression,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    body: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Statement.WhileStatement(lPar = lPar, condition = condition, rPar = rPar, body = body, supplement = supplement)

fun doWhileStatement(
    body: Node.Expression,
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    condition: Node.Expression,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Statement.DoWhileStatement(
    body = body,
    lPar = lPar,
    condition = condition,
    rPar = rPar,
    supplement = supplement
)

fun constructorClassParent(
    type: Node.Type.SimpleType,
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    arguments: List<Node.ValueArgument> = listOf(),
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.ClassOrObject.ConstructorClassParent(
    type = type,
    lPar = lPar,
    arguments = arguments,
    rPar = rPar,
    supplement = supplement
)

fun delegationClassParent(type: Node.Type, expression: Node.Expression, supplement: NodeSupplement = NodeSupplement()) =
    Node.Declaration.ClassOrObject.DelegationClassParent(type = type, expression = expression, supplement = supplement)

fun typeClassParent(type: Node.Type, supplement: NodeSupplement = NodeSupplement()) =
    Node.Declaration.ClassOrObject.TypeClassParent(type = type, supplement = supplement)

fun classBody(
    enumEntries: List<Node.Declaration.ClassOrObject.ClassBody.EnumEntry> = listOf(),
    declarations: List<Node.Declaration> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.ClassOrObject.ClassBody(
    enumEntries = enumEntries,
    declarations = declarations,
    supplement = supplement
)

fun enumEntry(
    modifiers: List<Node.Modifier> = listOf(),
    name: Node.Expression.NameExpression,
    lPar: Node.Keyword.LPar? = null,
    arguments: List<Node.ValueArgument> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    classBody: Node.Declaration.ClassOrObject.ClassBody? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.ClassOrObject.ClassBody.EnumEntry(
    modifiers = modifiers,
    name = name,
    lPar = if (arguments.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    arguments = arguments,
    rPar = if (arguments.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    classBody = classBody,
    supplement = supplement
)

fun initializer(block: Node.Expression.BlockExpression, supplement: NodeSupplement = NodeSupplement()) =
    Node.Declaration.ClassOrObject.ClassBody.Initializer(block = block, supplement = supplement)

fun secondaryConstructor(
    modifiers: List<Node.Modifier> = listOf(),
    constructorKeyword: Node.Keyword.Constructor = Node.Keyword.Constructor(),
    lPar: Node.Keyword.LPar? = null,
    parameters: List<Node.FunctionParameter> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    delegationCall: Node.Expression.CallExpression? = null,
    block: Node.Expression.BlockExpression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.ClassOrObject.ClassBody.SecondaryConstructor(
    modifiers = modifiers,
    constructorKeyword = constructorKeyword,
    lPar = if (parameters.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    parameters = parameters,
    rPar = if (parameters.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    delegationCall = delegationCall,
    block = block,
    supplement = supplement
)

fun classDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    declarationKeyword: Node.Declaration.ClassDeclaration.ClassOrInterfaceKeyword,
    name: Node.Expression.NameExpression,
    lAngle: Node.Keyword.Less? = null,
    typeParameters: List<Node.TypeParameter> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    primaryConstructor: Node.Declaration.ClassDeclaration.PrimaryConstructor? = null,
    parents: List<Node.Declaration.ClassOrObject.ClassParent> = listOf(),
    typeConstraintSet: Node.PostModifier.TypeConstraintSet? = null,
    body: Node.Declaration.ClassOrObject.ClassBody? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.ClassDeclaration(
    modifiers = modifiers,
    declarationKeyword = declarationKeyword,
    name = name,
    lAngle = if (typeParameters.isNotEmpty()) lAngle ?: Node.Keyword.Less() else lAngle,
    typeParameters = typeParameters,
    rAngle = if (typeParameters.isNotEmpty()) rAngle ?: Node.Keyword.Greater() else rAngle,
    primaryConstructor = primaryConstructor,
    parents = parents,
    typeConstraintSet = typeConstraintSet,
    body = body,
    supplement = supplement
)

fun primaryConstructor(
    modifiers: List<Node.Modifier> = listOf(),
    constructorKeyword: Node.Keyword.Constructor? = null,
    lPar: Node.Keyword.LPar? = null,
    parameters: List<Node.FunctionParameter> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.ClassDeclaration.PrimaryConstructor(
    modifiers = modifiers,
    constructorKeyword = constructorKeyword,
    lPar = if (parameters.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    parameters = parameters,
    rPar = if (parameters.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    supplement = supplement
)

fun objectDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    declarationKeyword: Node.Keyword.Object = Node.Keyword.Object(),
    name: Node.Expression.NameExpression? = null,
    parents: List<Node.Declaration.ClassOrObject.ClassParent> = listOf(),
    body: Node.Declaration.ClassOrObject.ClassBody? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.ObjectDeclaration(
    modifiers = modifiers,
    declarationKeyword = declarationKeyword,
    name = name,
    parents = parents,
    body = body,
    supplement = supplement
)

fun functionDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    lAngle: Node.Keyword.Less? = null,
    typeParameters: List<Node.TypeParameter> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    receiverType: Node.Type? = null,
    name: Node.Expression.NameExpression? = null,
    lPar: Node.Keyword.LPar? = null,
    parameters: List<Node.FunctionParameter> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    returnType: Node.Type? = null,
    postModifiers: List<Node.PostModifier> = listOf(),
    body: Node.Expression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.FunctionDeclaration(
    modifiers = modifiers,
    lAngle = if (typeParameters.isNotEmpty()) lAngle ?: Node.Keyword.Less() else lAngle,
    typeParameters = typeParameters,
    rAngle = if (typeParameters.isNotEmpty()) rAngle ?: Node.Keyword.Greater() else rAngle,
    receiverType = receiverType,
    name = name,
    lPar = if (parameters.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    parameters = parameters,
    rPar = if (parameters.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    returnType = returnType,
    postModifiers = postModifiers,
    body = body,
    supplement = supplement
)

fun propertyDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    valOrVarKeyword: Node.Keyword.ValOrVarKeyword,
    lAngle: Node.Keyword.Less? = null,
    typeParameters: List<Node.TypeParameter> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    receiverType: Node.Type? = null,
    lPar: Node.Keyword.LPar? = null,
    variables: List<Node.Variable> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    typeConstraintSet: Node.PostModifier.TypeConstraintSet? = null,
    initializerExpression: Node.Expression? = null,
    delegateExpression: Node.Expression? = null,
    accessors: List<Node.Declaration.PropertyDeclaration.Accessor> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.PropertyDeclaration(
    modifiers = modifiers,
    valOrVarKeyword = valOrVarKeyword,
    lAngle = if (typeParameters.isNotEmpty()) lAngle ?: Node.Keyword.Less() else lAngle,
    typeParameters = typeParameters,
    rAngle = if (typeParameters.isNotEmpty()) rAngle ?: Node.Keyword.Greater() else rAngle,
    receiverType = receiverType,
    lPar = if (variables.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    variables = variables,
    rPar = if (variables.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    typeConstraintSet = typeConstraintSet,
    initializerExpression = initializerExpression,
    delegateExpression = delegateExpression,
    accessors = accessors,
    supplement = supplement
)

fun getter(
    modifiers: List<Node.Modifier> = listOf(),
    lPar: Node.Keyword.LPar? = null,
    rPar: Node.Keyword.RPar? = null,
    type: Node.Type? = null,
    postModifiers: List<Node.PostModifier> = listOf(),
    body: Node.Expression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.PropertyDeclaration.Getter(
    modifiers = modifiers,
    lPar = if (body != null) lPar ?: Node.Keyword.LPar() else lPar,
    rPar = if (body != null) rPar ?: Node.Keyword.RPar() else rPar,
    type = type,
    postModifiers = postModifiers,
    body = body,
    supplement = supplement
)

fun setter(
    modifiers: List<Node.Modifier> = listOf(),
    lPar: Node.Keyword.LPar? = null,
    parameter: Node.FunctionParameter? = null,
    rPar: Node.Keyword.RPar? = null,
    postModifiers: List<Node.PostModifier> = listOf(),
    body: Node.Expression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.PropertyDeclaration.Setter(
    modifiers = modifiers,
    lPar = if (parameter != null) lPar ?: Node.Keyword.LPar() else lPar,
    parameter = parameter,
    rPar = if (parameter != null) rPar ?: Node.Keyword.RPar() else rPar,
    postModifiers = postModifiers,
    body = body,
    supplement = supplement
)

fun typeAliasDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    name: Node.Expression.NameExpression,
    lAngle: Node.Keyword.Less? = null,
    typeParameters: List<Node.TypeParameter> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    type: Node.Type,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.TypeAliasDeclaration(
    modifiers = modifiers,
    name = name,
    lAngle = if (typeParameters.isNotEmpty()) lAngle ?: Node.Keyword.Less() else lAngle,
    typeParameters = typeParameters,
    rAngle = if (typeParameters.isNotEmpty()) rAngle ?: Node.Keyword.Greater() else rAngle,
    type = type,
    supplement = supplement
)

fun parenthesizedType(
    modifiers: List<Node.Modifier> = listOf(),
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    innerType: Node.Type,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Type.ParenthesizedType(
    modifiers = modifiers,
    lPar = lPar,
    innerType = innerType,
    rPar = rPar,
    supplement = supplement
)

fun nullableType(
    modifiers: List<Node.Modifier> = listOf(),
    innerType: Node.Type,
    questionMark: Node.Keyword.Question = Node.Keyword.Question(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Type.NullableType(
    modifiers = modifiers,
    innerType = innerType,
    questionMark = questionMark,
    supplement = supplement
)

fun simpleType(
    modifiers: List<Node.Modifier> = listOf(),
    qualifiers: List<Node.Type.SimpleType.SimpleTypeQualifier> = listOf(),
    name: Node.Expression.NameExpression,
    lAngle: Node.Keyword.Less? = null,
    typeArguments: List<Node.TypeArgument> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Type.SimpleType(
    modifiers = modifiers,
    qualifiers = qualifiers,
    name = name,
    lAngle = lAngle,
    typeArguments = typeArguments,
    rAngle = rAngle,
    supplement = supplement
)

fun simpleTypeQualifier(
    name: Node.Expression.NameExpression,
    lAngle: Node.Keyword.Less? = null,
    typeArguments: List<Node.TypeArgument> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Type.SimpleType.SimpleTypeQualifier(
    name = name,
    lAngle = lAngle,
    typeArguments = typeArguments,
    rAngle = rAngle,
    supplement = supplement
)

fun dynamicType(modifiers: List<Node.Modifier> = listOf(), supplement: NodeSupplement = NodeSupplement()) =
    Node.Type.DynamicType(modifiers = modifiers, supplement = supplement)

fun functionType(
    modifiers: List<Node.Modifier> = listOf(),
    contextReceiver: Node.ContextReceiver? = null,
    receiverType: Node.Type? = null,
    lPar: Node.Keyword.LPar? = null,
    parameters: List<Node.Type.FunctionType.FunctionTypeParameter> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    returnType: Node.Type,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Type.FunctionType(
    modifiers = modifiers,
    contextReceiver = contextReceiver,
    receiverType = receiverType,
    lPar = if (parameters.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    parameters = parameters,
    rPar = if (parameters.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    returnType = returnType,
    supplement = supplement
)

fun functionTypeParameter(
    name: Node.Expression.NameExpression? = null,
    type: Node.Type,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Type.FunctionType.FunctionTypeParameter(name = name, type = type, supplement = supplement)

fun ifExpression(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    condition: Node.Expression,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    body: Node.Expression,
    elseBody: Node.Expression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.IfExpression(
    lPar = lPar,
    condition = condition,
    rPar = rPar,
    body = body,
    elseBody = elseBody,
    supplement = supplement
)

fun tryExpression(
    block: Node.Expression.BlockExpression,
    catchClauses: List<Node.Expression.TryExpression.CatchClause> = listOf(),
    finallyBlock: Node.Expression.BlockExpression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.TryExpression(
    block = block,
    catchClauses = catchClauses,
    finallyBlock = finallyBlock,
    supplement = supplement
)

fun catchClause(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    parameters: List<Node.FunctionParameter> = listOf(),
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    block: Node.Expression.BlockExpression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.TryExpression.CatchClause(
    lPar = lPar,
    parameters = parameters,
    rPar = rPar,
    block = block,
    supplement = supplement
)

fun whenExpression(
    whenKeyword: Node.Keyword.When = Node.Keyword.When(),
    subject: Node.Expression.WhenExpression.WhenSubject? = null,
    branches: List<Node.Expression.WhenExpression.WhenBranch> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.WhenExpression(
    whenKeyword = whenKeyword,
    subject = subject,
    branches = branches,
    supplement = supplement
)

fun whenSubject(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    variable: Node.Variable? = null,
    expression: Node.Expression,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.WhenExpression.WhenSubject(
    lPar = lPar,
    annotationSets = annotationSets,
    variable = variable,
    expression = expression,
    rPar = rPar,
    supplement = supplement
)

fun conditionalWhenBranch(
    conditions: List<Node.Expression.WhenExpression.WhenCondition> = listOf(),
    arrow: Node.Keyword.Arrow = Node.Keyword.Arrow(),
    body: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.WhenExpression.ConditionalWhenBranch(
    conditions = conditions,
    arrow = arrow,
    body = body,
    supplement = supplement
)

fun elseWhenBranch(
    arrow: Node.Keyword.Arrow = Node.Keyword.Arrow(),
    body: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.WhenExpression.ElseWhenBranch(arrow = arrow, body = body, supplement = supplement)

fun expressionWhenCondition(expression: Node.Expression, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.WhenExpression.ExpressionWhenCondition(expression = expression, supplement = supplement)

fun rangeWhenCondition(
    operator: Node.Expression.WhenExpression.WhenConditionRangeOperator,
    expression: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.WhenExpression.RangeWhenCondition(
    operator = operator,
    expression = expression,
    supplement = supplement
)

fun typeWhenCondition(
    operator: Node.Expression.WhenExpression.WhenConditionTypeOperator,
    type: Node.Type,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.WhenExpression.TypeWhenCondition(operator = operator, type = type, supplement = supplement)

fun throwExpression(expression: Node.Expression, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.ThrowExpression(expression = expression, supplement = supplement)

fun returnExpression(
    label: Node.Expression.NameExpression? = null,
    expression: Node.Expression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.ReturnExpression(label = label, expression = expression, supplement = supplement)

fun continueExpression(label: Node.Expression.NameExpression? = null, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.ContinueExpression(label = label, supplement = supplement)

fun breakExpression(label: Node.Expression.NameExpression? = null, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.BreakExpression(label = label, supplement = supplement)

fun blockExpression(statements: List<Node.Statement> = listOf(), supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.BlockExpression(statements = statements, supplement = supplement)

fun blockExpression(vararg statements: Node.Statement) = blockExpression(statements.toList())
fun callExpression(
    calleeExpression: Node.Expression,
    lAngle: Node.Keyword.Less? = null,
    typeArguments: List<Node.TypeArgument> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    lPar: Node.Keyword.LPar? = null,
    arguments: List<Node.ValueArgument> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    lambdaArgument: Node.Expression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.CallExpression(
    calleeExpression = calleeExpression,
    lAngle = if (typeArguments.isNotEmpty()) lAngle ?: Node.Keyword.Less() else lAngle,
    typeArguments = typeArguments,
    rAngle = if (typeArguments.isNotEmpty()) rAngle ?: Node.Keyword.Greater() else rAngle,
    lPar = if (arguments.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    arguments = arguments,
    rPar = if (arguments.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    lambdaArgument = lambdaArgument,
    supplement = supplement
)

fun lambdaExpression(
    parameters: List<Node.LambdaParameter> = listOf(),
    arrow: Node.Keyword.Arrow? = null,
    statements: List<Node.Statement> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.LambdaExpression(
    parameters = parameters,
    arrow = arrow,
    statements = statements,
    supplement = supplement
)

fun binaryExpression(
    lhs: Node.Expression,
    operator: Node.Expression.BinaryExpression.BinaryOperator,
    rhs: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.BinaryExpression(lhs = lhs, operator = operator, rhs = rhs, supplement = supplement)

fun prefixUnaryExpression(
    operator: Node.Expression.UnaryExpression.UnaryOperator,
    expression: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.PrefixUnaryExpression(operator = operator, expression = expression, supplement = supplement)

fun postfixUnaryExpression(
    expression: Node.Expression,
    operator: Node.Expression.UnaryExpression.UnaryOperator,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.PostfixUnaryExpression(expression = expression, operator = operator, supplement = supplement)

fun binaryTypeExpression(
    lhs: Node.Expression,
    operator: Node.Expression.BinaryTypeExpression.BinaryTypeOperator,
    rhs: Node.Type,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.BinaryTypeExpression(lhs = lhs, operator = operator, rhs = rhs, supplement = supplement)

fun callableReferenceExpression(
    lhs: Node.Expression? = null,
    questionMarks: List<Node.Keyword.Question> = listOf(),
    rhs: Node.Expression.NameExpression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.CallableReferenceExpression(
    lhs = lhs,
    questionMarks = questionMarks,
    rhs = rhs,
    supplement = supplement
)

fun classLiteralExpression(
    lhs: Node.Expression,
    questionMarks: List<Node.Keyword.Question> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.ClassLiteralExpression(lhs = lhs, questionMarks = questionMarks, supplement = supplement)

fun parenthesizedExpression(innerExpression: Node.Expression, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.ParenthesizedExpression(innerExpression = innerExpression, supplement = supplement)

fun stringLiteralExpression(
    entries: List<Node.Expression.StringLiteralExpression.StringEntry> = listOf(),
    raw: Boolean = false,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.StringLiteralExpression(entries = entries, raw = raw, supplement = supplement)

fun literalStringEntry(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.StringLiteralExpression.LiteralStringEntry(text = text, supplement = supplement)

fun escapeStringEntry(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.StringLiteralExpression.EscapeStringEntry(text = text, supplement = supplement)

fun templateStringEntry(
    expression: Node.Expression,
    short: Boolean = false,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.StringLiteralExpression.TemplateStringEntry(
    expression = expression,
    short = short,
    supplement = supplement
)

fun booleanLiteralExpression(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.BooleanLiteralExpression(text = text, supplement = supplement)

fun characterLiteralExpression(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.CharacterLiteralExpression(text = text, supplement = supplement)

fun integerLiteralExpression(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.IntegerLiteralExpression(text = text, supplement = supplement)

fun realLiteralExpression(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.RealLiteralExpression(text = text, supplement = supplement)

fun nullLiteralExpression(supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.NullLiteralExpression(supplement = supplement)

fun objectLiteralExpression(
    declaration: Node.Declaration.ObjectDeclaration,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.ObjectLiteralExpression(declaration = declaration, supplement = supplement)

fun collectionLiteralExpression(
    expressions: List<Node.Expression> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.CollectionLiteralExpression(expressions = expressions, supplement = supplement)

fun thisExpression(label: Node.Expression.NameExpression? = null, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.ThisExpression(label = label, supplement = supplement)

fun superExpression(
    typeArgument: Node.TypeArgument? = null,
    label: Node.Expression.NameExpression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.SuperExpression(typeArgument = typeArgument, label = label, supplement = supplement)

fun nameExpression(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.NameExpression(text = text, supplement = supplement)

fun labeledExpression(
    label: Node.Expression.NameExpression,
    statement: Node.Statement,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.LabeledExpression(label = label, statement = statement, supplement = supplement)

fun annotatedExpression(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    statement: Node.Statement,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.AnnotatedExpression(annotationSets = annotationSets, statement = statement, supplement = supplement)

fun indexedAccessExpression(
    expression: Node.Expression,
    indices: List<Node.Expression> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.IndexedAccessExpression(expression = expression, indices = indices, supplement = supplement)

fun anonymousFunctionExpression(
    function: Node.Declaration.FunctionDeclaration,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.AnonymousFunctionExpression(function = function, supplement = supplement)

fun typeParameter(
    modifiers: List<Node.Modifier> = listOf(),
    name: Node.Expression.NameExpression,
    type: Node.Type? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.TypeParameter(modifiers = modifiers, name = name, type = type, supplement = supplement)

fun functionParameter(
    modifiers: List<Node.Modifier> = listOf(),
    valOrVarKeyword: Node.Keyword.ValOrVarKeyword? = null,
    name: Node.Expression.NameExpression,
    type: Node.Type? = null,
    defaultValue: Node.Expression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.FunctionParameter(
    modifiers = modifiers,
    valOrVarKeyword = valOrVarKeyword,
    name = name,
    type = type,
    defaultValue = defaultValue,
    supplement = supplement
)

fun lambdaParameter(
    lPar: Node.Keyword.LPar? = null,
    variables: List<Node.Variable> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    destructuringType: Node.Type? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.LambdaParameter(
    lPar = lPar,
    variables = variables,
    rPar = rPar,
    destructuringType = destructuringType,
    supplement = supplement
)

fun variable(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    name: Node.Expression.NameExpression,
    type: Node.Type? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Variable(annotationSets = annotationSets, name = name, type = type, supplement = supplement)

fun typeArgument(
    modifiers: List<Node.Modifier> = listOf(),
    type: Node.Type,
    supplement: NodeSupplement = NodeSupplement()
) = Node.TypeArgument(modifiers = modifiers, type = type, supplement = supplement)

fun valueArgument(
    name: Node.Expression.NameExpression? = null,
    spreadOperator: Node.Keyword.Asterisk? = null,
    expression: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.ValueArgument(name = name, spreadOperator = spreadOperator, expression = expression, supplement = supplement)

fun contextReceiver(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    receiverTypes: List<Node.Type> = listOf(),
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.ContextReceiver(lPar = lPar, receiverTypes = receiverTypes, rPar = rPar, supplement = supplement)

fun annotationSet(
    target: Node.Modifier.AnnotationSet.AnnotationTarget? = null,
    lBracket: Node.Keyword.LBracket? = null,
    annotations: List<Node.Modifier.AnnotationSet.Annotation> = listOf(),
    rBracket: Node.Keyword.RBracket? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Modifier.AnnotationSet(
    target = target,
    lBracket = if (annotations.size >= 2) lBracket ?: Node.Keyword.LBracket() else lBracket,
    annotations = annotations,
    rBracket = if (annotations.size >= 2) rBracket ?: Node.Keyword.RBracket() else rBracket,
    supplement = supplement
)

fun annotation(
    type: Node.Type.SimpleType,
    lPar: Node.Keyword.LPar? = null,
    arguments: List<Node.ValueArgument> = listOf(),
    rPar: Node.Keyword.RPar? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Modifier.AnnotationSet.Annotation(
    type = type,
    lPar = if (arguments.isNotEmpty()) lPar ?: Node.Keyword.LPar() else lPar,
    arguments = arguments,
    rPar = if (arguments.isNotEmpty()) rPar ?: Node.Keyword.RPar() else rPar,
    supplement = supplement
)

fun typeConstraintSet(
    constraints: List<Node.PostModifier.TypeConstraintSet.TypeConstraint> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.PostModifier.TypeConstraintSet(constraints = constraints, supplement = supplement)

fun typeConstraint(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    name: Node.Expression.NameExpression,
    type: Node.Type,
    supplement: NodeSupplement = NodeSupplement()
) = Node.PostModifier.TypeConstraintSet.TypeConstraint(
    annotationSets = annotationSets,
    name = name,
    type = type,
    supplement = supplement
)

fun contract(
    lBracket: Node.Keyword.LBracket = Node.Keyword.LBracket(),
    contractEffects: List<Node.Expression> = listOf(),
    rBracket: Node.Keyword.RBracket = Node.Keyword.RBracket(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.PostModifier.Contract(
    lBracket = lBracket,
    contractEffects = contractEffects,
    rBracket = rBracket,
    supplement = supplement
)

fun whitespace(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Extra.Whitespace(text = text, supplement = supplement)

fun comment(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Extra.Comment(text = text, supplement = supplement)

fun semicolon(supplement: NodeSupplement = NodeSupplement()) = Node.Extra.Semicolon(supplement = supplement)
fun trailingComma(supplement: NodeSupplement = NodeSupplement()) = Node.Extra.TrailingComma(supplement = supplement)
