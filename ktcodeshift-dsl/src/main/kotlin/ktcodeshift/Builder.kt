package ktcodeshift

import ktast.ast.Node
import ktast.ast.NodeSupplement

/**
 * Creates a new [Node.KotlinFile] instance.
 */
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

/**
 * Creates a new [Node.KotlinScript] instance.
 */
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

/**
 * Creates a new [Node.PackageDirective] instance.
 */
fun packageDirective(
    names: List<Node.Expression.NameExpression> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.PackageDirective(names = names, supplement = supplement)

/**
 * Creates a new [Node.ImportDirective] instance.
 */
fun importDirective(
    names: List<Node.Expression.NameExpression> = listOf(),
    aliasName: Node.Expression.NameExpression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.ImportDirective(names = names, aliasName = aliasName, supplement = supplement)

/**
 * Creates a new [Node.Statement.ForStatement] instance.
 */
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

/**
 * Creates a new [Node.Statement.WhileStatement] instance.
 */
fun whileStatement(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    condition: Node.Expression,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    body: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Statement.WhileStatement(lPar = lPar, condition = condition, rPar = rPar, body = body, supplement = supplement)

/**
 * Creates a new [Node.Statement.DoWhileStatement] instance.
 */
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

/**
 * Creates a new [Node.Declaration.ClassOrObject.ConstructorClassParent] instance.
 */
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

/**
 * Creates a new [Node.Declaration.ClassOrObject.DelegationClassParent] instance.
 */
fun delegationClassParent(type: Node.Type, expression: Node.Expression, supplement: NodeSupplement = NodeSupplement()) =
    Node.Declaration.ClassOrObject.DelegationClassParent(type = type, expression = expression, supplement = supplement)

/**
 * Creates a new [Node.Declaration.ClassOrObject.TypeClassParent] instance.
 */
fun typeClassParent(type: Node.Type, supplement: NodeSupplement = NodeSupplement()) =
    Node.Declaration.ClassOrObject.TypeClassParent(type = type, supplement = supplement)

/**
 * Creates a new [Node.Declaration.ClassOrObject.ClassBody] instance.
 */
fun classBody(
    enumEntries: List<Node.Declaration.ClassOrObject.ClassBody.EnumEntry> = listOf(),
    declarations: List<Node.Declaration> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.ClassOrObject.ClassBody(
    enumEntries = enumEntries,
    declarations = declarations,
    supplement = supplement
)

/**
 * Creates a new [Node.Declaration.ClassOrObject.ClassBody.EnumEntry] instance.
 */
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

/**
 * Creates a new [Node.Declaration.ClassOrObject.ClassBody.Initializer] instance.
 */
fun initializer(block: Node.Expression.BlockExpression, supplement: NodeSupplement = NodeSupplement()) =
    Node.Declaration.ClassOrObject.ClassBody.Initializer(block = block, supplement = supplement)

/**
 * Creates a new [Node.Declaration.ClassOrObject.ClassBody.SecondaryConstructor] instance.
 */
fun secondaryConstructor(
    modifiers: List<Node.Modifier> = listOf(),
    constructorKeyword: Node.Keyword.Constructor = Node.Keyword.Constructor(),
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    parameters: List<Node.FunctionParameter> = listOf(),
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    delegationCall: Node.Expression.CallExpression? = null,
    block: Node.Expression.BlockExpression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.ClassOrObject.ClassBody.SecondaryConstructor(
    modifiers = modifiers,
    constructorKeyword = constructorKeyword,
    lPar = lPar,
    parameters = parameters,
    rPar = rPar,
    delegationCall = delegationCall,
    block = block,
    supplement = supplement
)

/**
 * Creates a new [Node.Declaration.ClassDeclaration] instance.
 */
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

/**
 * Creates a new [Node.Declaration.ClassDeclaration.PrimaryConstructor] instance.
 */
fun primaryConstructor(
    modifiers: List<Node.Modifier> = listOf(),
    constructorKeyword: Node.Keyword.Constructor? = null,
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    parameters: List<Node.FunctionParameter> = listOf(),
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Declaration.ClassDeclaration.PrimaryConstructor(
    modifiers = modifiers,
    constructorKeyword = constructorKeyword,
    lPar = lPar,
    parameters = parameters,
    rPar = rPar,
    supplement = supplement
)

/**
 * Creates a new [Node.Declaration.ObjectDeclaration] instance.
 */
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

/**
 * Creates a new [Node.Declaration.FunctionDeclaration] instance.
 */
fun functionDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    lAngle: Node.Keyword.Less? = null,
    typeParameters: List<Node.TypeParameter> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    receiverType: Node.Type? = null,
    name: Node.Expression.NameExpression? = null,
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    parameters: List<Node.FunctionParameter> = listOf(),
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
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
    lPar = lPar,
    parameters = parameters,
    rPar = rPar,
    returnType = returnType,
    postModifiers = postModifiers,
    body = body,
    supplement = supplement
)

/**
 * Creates a new [Node.Declaration.PropertyDeclaration] instance.
 */
fun propertyDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    valOrVarKeyword: Node.Keyword.ValOrVarKeyword,
    lAngle: Node.Keyword.Less? = null,
    typeParameters: List<Node.TypeParameter> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    receiverType: Node.Type? = null,
    lPar: Node.Keyword.LPar? = null,
    variables: List<Node.Variable>,
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
    lPar = lPar ?: Node.Keyword.LPar(),
    variables = variables,
    rPar = rPar ?: Node.Keyword.RPar(),
    typeConstraintSet = typeConstraintSet,
    initializerExpression = initializerExpression,
    delegateExpression = delegateExpression,
    accessors = accessors,
    supplement = supplement
)

/**
 * Creates a new [Node.Declaration.PropertyDeclaration] instance.
 */
fun propertyDeclaration(
    modifiers: List<Node.Modifier> = listOf(),
    valOrVarKeyword: Node.Keyword.ValOrVarKeyword,
    lAngle: Node.Keyword.Less? = null,
    typeParameters: List<Node.TypeParameter> = listOf(),
    rAngle: Node.Keyword.Greater? = null,
    receiverType: Node.Type? = null,
    variable: Node.Variable,
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
    lPar = null,
    variables = listOf(variable),
    rPar = null,
    typeConstraintSet = typeConstraintSet,
    initializerExpression = initializerExpression,
    delegateExpression = delegateExpression,
    accessors = accessors,
    supplement = supplement
)

/**
 * Creates a new [Node.Declaration.PropertyDeclaration.Getter] instance.
 */
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

/**
 * Creates a new [Node.Declaration.PropertyDeclaration.Setter] instance.
 */
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

/**
 * Creates a new [Node.Declaration.TypeAliasDeclaration] instance.
 */
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

/**
 * Creates a new [Node.Type.ParenthesizedType] instance.
 */
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

/**
 * Creates a new [Node.Type.NullableType] instance.
 */
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

/**
 * Creates a new [Node.Type.SimpleType] instance.
 */
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

/**
 * Creates a new [Node.Type.SimpleType.SimpleTypeQualifier] instance.
 */
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

/**
 * Creates a new [Node.Type.DynamicType] instance.
 */
fun dynamicType(modifiers: List<Node.Modifier> = listOf(), supplement: NodeSupplement = NodeSupplement()) =
    Node.Type.DynamicType(modifiers = modifiers, supplement = supplement)

/**
 * Creates a new [Node.Type.FunctionType] instance.
 */
fun functionType(
    modifiers: List<Node.Modifier> = listOf(),
    contextReceiver: Node.ContextReceiver? = null,
    receiverType: Node.Type? = null,
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    parameters: List<Node.Type.FunctionType.FunctionTypeParameter> = listOf(),
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    returnType: Node.Type,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Type.FunctionType(
    modifiers = modifiers,
    contextReceiver = contextReceiver,
    receiverType = receiverType,
    lPar = lPar,
    parameters = parameters,
    rPar = rPar,
    returnType = returnType,
    supplement = supplement
)

/**
 * Creates a new [Node.Type.FunctionType.FunctionTypeParameter] instance.
 */
fun functionTypeParameter(
    name: Node.Expression.NameExpression? = null,
    type: Node.Type,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Type.FunctionType.FunctionTypeParameter(name = name, type = type, supplement = supplement)

/**
 * Creates a new [Node.Expression.IfExpression] instance.
 */
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

/**
 * Creates a new [Node.Expression.TryExpression] instance.
 */
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

/**
 * Creates a new [Node.Expression.TryExpression.CatchClause] instance.
 */
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

/**
 * Creates a new [Node.Expression.WhenExpression] instance.
 */
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

/**
 * Creates a new [Node.Expression.WhenExpression.WhenSubject] instance.
 */
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

/**
 * Creates a new [Node.Expression.WhenExpression.ConditionalWhenBranch] instance.
 */
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

/**
 * Creates a new [Node.Expression.WhenExpression.ElseWhenBranch] instance.
 */
fun elseWhenBranch(
    arrow: Node.Keyword.Arrow = Node.Keyword.Arrow(),
    body: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.WhenExpression.ElseWhenBranch(arrow = arrow, body = body, supplement = supplement)

/**
 * Creates a new [Node.Expression.WhenExpression.ExpressionWhenCondition] instance.
 */
fun expressionWhenCondition(expression: Node.Expression, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.WhenExpression.ExpressionWhenCondition(expression = expression, supplement = supplement)

/**
 * Creates a new [Node.Expression.WhenExpression.RangeWhenCondition] instance.
 */
fun rangeWhenCondition(
    operator: Node.Expression.WhenExpression.WhenConditionRangeOperator,
    expression: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.WhenExpression.RangeWhenCondition(
    operator = operator,
    expression = expression,
    supplement = supplement
)

/**
 * Creates a new [Node.Expression.WhenExpression.TypeWhenCondition] instance.
 */
fun typeWhenCondition(
    operator: Node.Expression.WhenExpression.WhenConditionTypeOperator,
    type: Node.Type,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.WhenExpression.TypeWhenCondition(operator = operator, type = type, supplement = supplement)

/**
 * Creates a new [Node.Expression.ThrowExpression] instance.
 */
fun throwExpression(expression: Node.Expression, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.ThrowExpression(expression = expression, supplement = supplement)

/**
 * Creates a new [Node.Expression.ReturnExpression] instance.
 */
fun returnExpression(
    label: Node.Expression.NameExpression? = null,
    expression: Node.Expression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.ReturnExpression(label = label, expression = expression, supplement = supplement)

/**
 * Creates a new [Node.Expression.ContinueExpression] instance.
 */
fun continueExpression(label: Node.Expression.NameExpression? = null, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.ContinueExpression(label = label, supplement = supplement)

/**
 * Creates a new [Node.Expression.BreakExpression] instance.
 */
fun breakExpression(label: Node.Expression.NameExpression? = null, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.BreakExpression(label = label, supplement = supplement)

/**
 * Creates a new [Node.Expression.BlockExpression] instance.
 */
fun blockExpression(statements: List<Node.Statement> = listOf(), supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.BlockExpression(statements = statements, supplement = supplement)

/**
 * Creates a new [Node.Expression.BlockExpression] instance.
 */
fun blockExpression(vararg statements: Node.Statement) = blockExpression(statements.toList())

/**
 * Creates a new [Node.Expression.CallExpression] instance.
 */
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
    lPar = if (arguments.isNotEmpty() || lambdaArgument == null) lPar ?: Node.Keyword.LPar() else lPar,
    arguments = arguments,
    rPar = if (arguments.isNotEmpty() || lambdaArgument == null) rPar ?: Node.Keyword.RPar() else rPar,
    lambdaArgument = lambdaArgument,
    supplement = supplement
)

/**
 * Creates a new [Node.Expression.LambdaExpression] instance.
 */
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

/**
 * Creates a new [Node.Expression.BinaryExpression] instance.
 */
fun binaryExpression(
    lhs: Node.Expression,
    operator: Node.Expression.BinaryExpression.BinaryOperator,
    rhs: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.BinaryExpression(lhs = lhs, operator = operator, rhs = rhs, supplement = supplement)

/**
 * Creates a new [Node.Expression.PrefixUnaryExpression] instance.
 */
fun prefixUnaryExpression(
    operator: Node.Expression.UnaryExpression.UnaryOperator,
    expression: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.PrefixUnaryExpression(operator = operator, expression = expression, supplement = supplement)

/**
 * Creates a new [Node.Expression.PostfixUnaryExpression] instance.
 */
fun postfixUnaryExpression(
    expression: Node.Expression,
    operator: Node.Expression.UnaryExpression.UnaryOperator,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.PostfixUnaryExpression(expression = expression, operator = operator, supplement = supplement)

/**
 * Creates a new [Node.Expression.BinaryTypeExpression] instance.
 */
fun binaryTypeExpression(
    lhs: Node.Expression,
    operator: Node.Expression.BinaryTypeExpression.BinaryTypeOperator,
    rhs: Node.Type,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.BinaryTypeExpression(lhs = lhs, operator = operator, rhs = rhs, supplement = supplement)

/**
 * Creates a new [Node.Expression.CallableReferenceExpression] instance.
 */
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

/**
 * Creates a new [Node.Expression.ClassLiteralExpression] instance.
 */
fun classLiteralExpression(
    lhs: Node.Expression,
    questionMarks: List<Node.Keyword.Question> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.ClassLiteralExpression(lhs = lhs, questionMarks = questionMarks, supplement = supplement)

/**
 * Creates a new [Node.Expression.ParenthesizedExpression] instance.
 */
fun parenthesizedExpression(innerExpression: Node.Expression, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.ParenthesizedExpression(innerExpression = innerExpression, supplement = supplement)

/**
 * Creates a new [Node.Expression.StringLiteralExpression] instance.
 */
fun stringLiteralExpression(
    entries: List<Node.Expression.StringLiteralExpression.StringEntry> = listOf(),
    raw: Boolean = false,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.StringLiteralExpression(entries = entries, raw = raw, supplement = supplement)

/**
 * Creates a new [Node.Expression.StringLiteralExpression.LiteralStringEntry] instance.
 */
fun literalStringEntry(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.StringLiteralExpression.LiteralStringEntry(text = text, supplement = supplement)

/**
 * Creates a new [Node.Expression.StringLiteralExpression.EscapeStringEntry] instance.
 */
fun escapeStringEntry(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.StringLiteralExpression.EscapeStringEntry(text = text, supplement = supplement)

/**
 * Creates a new [Node.Expression.StringLiteralExpression.TemplateStringEntry] instance.
 */
fun templateStringEntry(
    expression: Node.Expression,
    short: Boolean = false,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.StringLiteralExpression.TemplateStringEntry(
    expression = expression,
    short = short,
    supplement = supplement
)

/**
 * Creates a new [Node.Expression.BooleanLiteralExpression] instance.
 */
fun booleanLiteralExpression(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.BooleanLiteralExpression(text = text, supplement = supplement)

/**
 * Creates a new [Node.Expression.CharacterLiteralExpression] instance.
 */
fun characterLiteralExpression(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.CharacterLiteralExpression(text = text, supplement = supplement)

/**
 * Creates a new [Node.Expression.IntegerLiteralExpression] instance.
 */
fun integerLiteralExpression(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.IntegerLiteralExpression(text = text, supplement = supplement)

/**
 * Creates a new [Node.Expression.RealLiteralExpression] instance.
 */
fun realLiteralExpression(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.RealLiteralExpression(text = text, supplement = supplement)

/**
 * Creates a new [Node.Expression.NullLiteralExpression] instance.
 */
fun nullLiteralExpression(supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.NullLiteralExpression(supplement = supplement)

/**
 * Creates a new [Node.Expression.ObjectLiteralExpression] instance.
 */
fun objectLiteralExpression(
    declaration: Node.Declaration.ObjectDeclaration,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.ObjectLiteralExpression(declaration = declaration, supplement = supplement)

/**
 * Creates a new [Node.Expression.CollectionLiteralExpression] instance.
 */
fun collectionLiteralExpression(
    expressions: List<Node.Expression> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.CollectionLiteralExpression(expressions = expressions, supplement = supplement)

/**
 * Creates a new [Node.Expression.ThisExpression] instance.
 */
fun thisExpression(label: Node.Expression.NameExpression? = null, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.ThisExpression(label = label, supplement = supplement)

/**
 * Creates a new [Node.Expression.SuperExpression] instance.
 */
fun superExpression(
    typeArgument: Node.TypeArgument? = null,
    label: Node.Expression.NameExpression? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.SuperExpression(typeArgument = typeArgument, label = label, supplement = supplement)

/**
 * Creates a new [Node.Expression.NameExpression] instance.
 */
fun nameExpression(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Expression.NameExpression(text = text, supplement = supplement)

/**
 * Creates a new [Node.Expression.LabeledExpression] instance.
 */
fun labeledExpression(
    label: Node.Expression.NameExpression,
    statement: Node.Statement,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.LabeledExpression(label = label, statement = statement, supplement = supplement)

/**
 * Creates a new [Node.Expression.AnnotatedExpression] instance.
 */
fun annotatedExpression(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    statement: Node.Statement,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.AnnotatedExpression(annotationSets = annotationSets, statement = statement, supplement = supplement)

/**
 * Creates a new [Node.Expression.IndexedAccessExpression] instance.
 */
fun indexedAccessExpression(
    expression: Node.Expression,
    indices: List<Node.Expression> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.IndexedAccessExpression(expression = expression, indices = indices, supplement = supplement)

/**
 * Creates a new [Node.Expression.AnonymousFunctionExpression] instance.
 */
fun anonymousFunctionExpression(
    function: Node.Declaration.FunctionDeclaration,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Expression.AnonymousFunctionExpression(function = function, supplement = supplement)

/**
 * Creates a new [Node.TypeParameter] instance.
 */
fun typeParameter(
    modifiers: List<Node.Modifier> = listOf(),
    name: Node.Expression.NameExpression,
    type: Node.Type? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.TypeParameter(modifiers = modifiers, name = name, type = type, supplement = supplement)

/**
 * Creates a new [Node.FunctionParameter] instance.
 */
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

/**
 * Creates a new [Node.LambdaParameter] instance.
 */
fun lambdaParameter(
    lPar: Node.Keyword.LPar? = null,
    variables: List<Node.Variable>,
    rPar: Node.Keyword.RPar? = null,
    destructuringType: Node.Type? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.LambdaParameter(
    lPar = lPar ?: Node.Keyword.LPar(),
    variables = variables,
    rPar = rPar ?: Node.Keyword.RPar(),
    destructuringType = destructuringType,
    supplement = supplement
)

/**
 * Creates a new [Node.LambdaParameter] instance.
 */
fun lambdaParameter(variable: Node.Variable, supplement: NodeSupplement = NodeSupplement()) = Node.LambdaParameter(
    lPar = null,
    variables = listOf(variable),
    rPar = null,
    destructuringType = null,
    supplement = supplement
)

/**
 * Creates a new [Node.Variable] instance.
 */
fun variable(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    name: Node.Expression.NameExpression,
    type: Node.Type? = null,
    supplement: NodeSupplement = NodeSupplement()
) = Node.Variable(annotationSets = annotationSets, name = name, type = type, supplement = supplement)

/**
 * Creates a new [Node.TypeArgument] instance.
 */
fun typeArgument(
    modifiers: List<Node.Modifier> = listOf(),
    type: Node.Type,
    supplement: NodeSupplement = NodeSupplement()
) = Node.TypeArgument(modifiers = modifiers, type = type, supplement = supplement)

/**
 * Creates a new [Node.ValueArgument] instance.
 */
fun valueArgument(
    name: Node.Expression.NameExpression? = null,
    spreadOperator: Node.Keyword.Asterisk? = null,
    expression: Node.Expression,
    supplement: NodeSupplement = NodeSupplement()
) = Node.ValueArgument(name = name, spreadOperator = spreadOperator, expression = expression, supplement = supplement)

/**
 * Creates a new [Node.ContextReceiver] instance.
 */
fun contextReceiver(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    receiverTypes: List<Node.Type> = listOf(),
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.ContextReceiver(lPar = lPar, receiverTypes = receiverTypes, rPar = rPar, supplement = supplement)

/**
 * Creates a new [Node.Modifier.AnnotationSet] instance.
 */
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

/**
 * Creates a new [Node.Modifier.AnnotationSet.Annotation] instance.
 */
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

/**
 * Creates a new [Node.PostModifier.TypeConstraintSet] instance.
 */
fun typeConstraintSet(
    constraints: List<Node.PostModifier.TypeConstraintSet.TypeConstraint> = listOf(),
    supplement: NodeSupplement = NodeSupplement()
) = Node.PostModifier.TypeConstraintSet(constraints = constraints, supplement = supplement)

/**
 * Creates a new [Node.PostModifier.TypeConstraintSet.TypeConstraint] instance.
 */
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

/**
 * Creates a new [Node.PostModifier.Contract] instance.
 */
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

/**
 * Creates a new [Node.Extra.Whitespace] instance.
 */
fun whitespace(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Extra.Whitespace(text = text, supplement = supplement)

/**
 * Creates a new [Node.Extra.Comment] instance.
 */
fun comment(text: String, supplement: NodeSupplement = NodeSupplement()) =
    Node.Extra.Comment(text = text, supplement = supplement)

/**
 * Creates a new [Node.Extra.Semicolon] instance.
 */
fun semicolon(supplement: NodeSupplement = NodeSupplement()) = Node.Extra.Semicolon(supplement = supplement)

/**
 * Creates a new [Node.Extra.TrailingComma] instance.
 */
fun trailingComma(supplement: NodeSupplement = NodeSupplement()) = Node.Extra.TrailingComma(supplement = supplement)
