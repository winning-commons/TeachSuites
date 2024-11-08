import requests

url = "http://localhost:3000"


def testSA():
    question = "Describe one adaptation penguins have developed to survive in cold environments."
    answers = [
        "Penguins are black and white, which helps them survive.",
        "Penguins have a layer of blubber that keeps them warm in cold water.",
        "Penguins have developed multiple adaptations to survive extreme cold, including a thick layer of blubber, densely packed waterproof feathers, and a unique counter-current heat exchange system in their flippers and legs. The blubber layer provides insulation by trapping body heat, while the tightly packed feathers create a waterproof barrier that keeps their skin dry, further reducing heat loss. Additionally, the counter-current heat exchange system allows penguins to conserve body heat by transferring warmth from blood vessels heading to the extremities to those returning to the body, which minimizes heat loss in their exposed limbs. This combination of adaptations enables penguins to thrive in temperatures that would be fatal for most other animals.",
    ]
    # for answer in range(1):
    for answer in range(len(answers)):
        answer = answers[answer]
        body = {
            "sa-input": question,
            "sa-question": answer,
        }

        endpoint = "/api/grade-sa"
        response = requests.post(url + endpoint, json=body)
        print("*" * 20)
        for key in response.json():
            print(key + ": " + response.json()[key])

        assert response.status_code == 200


testSA()
