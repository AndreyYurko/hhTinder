def vocab_to_json(arr):
    res = {}

    for el in arr:
        res[el[0]] = el[1]

    return res
