package com.xebia.functional.chains

import cats.effect.IO

import com.xebia.functional.chains.mock.OpenAIClientMock
import com.xebia.functional.chains.mock.VectorStoreMock
import com.xebia.functional.chains.models.InvalidChainInputError
import com.xebia.functional.chains.models.InvalidChainInputsError
import com.xebia.functional.chains.models.InvalidCombineDocumentsChainError
import com.xebia.functional.chains.retrievalqa.VectorQAChain
import munit.CatsEffectSuite
import eu.timepit.refined.types.string.NonEmptyString

class VectorQAChainSpec extends CatsEffectSuite:

  val outputVariable = NonEmptyString.unsafeFrom("answer")

  test("run should return the answer from the LLMChain") {
    val vectorStore = VectorStoreMock.make
    val qa = VectorQAChain.makeWithDefaults[IO](OpenAIClientMock.make, vectorStore, "testing", outputVariable)
    val result = qa.run("What do you think?")

    assertIO(result, TestData.outputIDK)
  }

  test("run should return the answer from the LLMChain when using question explicitly in the inputs") {
    val vectorStore = VectorStoreMock.make
    val qa = VectorQAChain.makeWithDefaults[IO](OpenAIClientMock.make, vectorStore, "testing", outputVariable)
    val result = qa.run(Map("question" -> "What do you think?"))

    assertIO(result, TestData.outputIDK)
  }

  test("run should return the answer from the LLMChain when using question explicitly in the inputs") {
    val vectorStore = VectorStoreMock.make
    val qa = VectorQAChain.makeWithDefaults[IO](OpenAIClientMock.make, vectorStore, "testing", outputVariable)
    val result = qa.run(Map("question" -> "What do you think?"))

    assertIO(result, TestData.outputIDK)
  }

  test("run should return the answer from the LLMChain when the input is more than one") {
    val vectorStore = VectorStoreMock.make
    val qa = VectorQAChain.makeWithDefaults[IO](OpenAIClientMock.make, vectorStore, "testing", outputVariable)
    val result = qa.run(Map("question" -> "What do you think?", "foo" -> "bla bla bla"))

    assertIO(result, TestData.outputIDK)
  }

  test("run should fail with an InvalidChainInputsError if the inputs don't match the expected") {
    val vectorStore = VectorStoreMock.make
    val qa = VectorQAChain.makeWithDefaults[IO](OpenAIClientMock.make, vectorStore, "testing", outputVariable)
    val result = qa.run(Map("foo" -> "What do you think?"))

    interceptMessageIO[InvalidChainInputsError](
      "The provided inputs (foo) do not match with chain's inputs (question)"
    )(result)
  }
